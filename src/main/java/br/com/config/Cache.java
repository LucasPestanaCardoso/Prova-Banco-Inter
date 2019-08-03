package br.com.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class Cache<T> {

	private final Map<String, T> digitos;
	private final Map<String, Long> tempo;
	private final long defaultTempo;
	private final ExecutorService threads;

	public Cache() {
		this(10000000);
	}

	public Cache(final long defaultTempo) {
		this.digitos = Collections.synchronizedMap(new HashMap<String, T>());
		this.tempo = Collections.synchronizedMap(new HashMap<String, Long>());

		this.defaultTempo = defaultTempo;

		this.threads = Executors.newFixedThreadPool(256);
		Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(this.removerExpirados(), this.defaultTempo / 2,
				this.defaultTempo, TimeUnit.SECONDS);
	}

	private final Runnable removerExpirados() {
		return new Runnable() {
			public void run() {
				for (final String name : tempo.keySet()) {
					if (System.currentTimeMillis() > tempo.get(name)) {
						threads.execute(createRemoveRunnable(name));
					}
				}
			}
		};
	}

	private final Runnable createRemoveRunnable(final String key) {
		return new Runnable() {
			public void run() {
				digitos.remove(key);
				tempo.remove(key);
			}
		};
	}

	public void put(final String key, final T obj) {
		this.put(key, obj, this.defaultTempo);
	}

	public void put(final String key, final T obj, final long tempo) {
		this.digitos.put(key, obj);
		this.tempo.put(key, System.currentTimeMillis() + tempo * 1000);
		
		//Remover o ultimo da Lista
		if(this.size() > 10) {			
			this.threads.execute(this.createRemoveRunnable(registroMaisVelho()));
		}
	}
	
	public int size() {
		return this.digitos.size();
	}
	
	public String registroMaisVelho() {
		Long minTime = Long.MAX_VALUE;
		String key = "";
		
		for(Map.Entry<String, Long> entry : tempo.entrySet()) {
			
			String chave = entry.getKey();
			Long tempo = entry.getValue();
			
			if(tempo < minTime) {
				minTime = tempo;
				key = chave;
			}
		}
		
		return key;
	}

	public T get(final String key) {
		final Long expireTime = this.tempo.get(key);
		if (expireTime == null)
			return null;
		if (System.currentTimeMillis() > expireTime) {
			this.threads.execute(this.createRemoveRunnable(key));
			return null;
		}
		return this.digitos.get(key);
	}

	@SuppressWarnings("unchecked")
	public <R extends T> R get(final String key, final Class<R> type) {
		return (R) this.get(key);
	}

}
