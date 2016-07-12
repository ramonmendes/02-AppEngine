/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cit.lab.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cit.lab.constant.Constantes;
import br.com.cit.lab.model.Client;
import br.com.cit.lab.model.Phone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ListarTelefone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ListarTelefone.class
			.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><head><title>Lista Client e Usuario</title><style>table, th, td {border: 1px solid black;}</style></head><body><table><tr><td>Client</td><td>Phone</td></tr>");

	    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    @SuppressWarnings("unchecked")
		List<Client> list = (List<Client>)syncCache.get(Constantes.KEY_CLIENTS);
		
	    if(list!=null && list.size()>0){
			log.info("Use Caching!");
			for (Client client : list) {
				print(buffer, client);
			}
		}else{
			log.info("Not Use Caching!");
			list = new ArrayList<Client>();
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Query queryUsuario = new Query(Client.class.getSimpleName());
			PreparedQuery resul = datastore.prepare(queryUsuario);
			
			if (resul != null) {
				for (Entity usuarioEntity: resul.asIterable()) {
					Object fieldName = usuarioEntity.getProperty(Constantes.NAME);
					Key phoneRef = ((Key)usuarioEntity.getProperty(Constantes.PHONE));
					
					try {
						Entity phoneEntity = datastore.get(phoneRef);
						Client client = new Client(fieldName.toString(), new Phone((String)phoneEntity.getProperty(Constantes.PHONE)));
						list.add(client);
						print(buffer, client);
					} catch (EntityNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				syncCache.put(Constantes.KEY_CLIENTS, list);
			}
		}

		buffer.append("</table></body></html>");
		log.info("List User`s and Telefone");

		response.setContentType("text/html");
		response.getWriter().print(buffer.toString());

	}

	private void print(StringBuffer buffer, Client client) {
		buffer.append("<tr><td>"+client.getName()+"</td><td>"+client.getTelefone().getPhone()+"</td></tr>");
	}
}
