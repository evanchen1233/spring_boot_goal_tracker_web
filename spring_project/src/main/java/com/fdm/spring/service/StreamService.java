package com.fdm.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.spring.dal.StreamRepository;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.Stream;

/**
 * This is a Spring service class for Stream. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class StreamService {
	
	@Autowired
	StreamRepository streamRepository;
	
	/**
	 * 
	 * @param id stream id
	 * @return stream object by given stream id
	 */
	public Stream getStreamById(int id) {
		
		Optional<Stream> stream = streamRepository.findById(id);
		
		if(stream.isPresent())			// process optional
			return stream.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @param streamName stream name
	 * @return stream object by given stream name
	 */
	public Stream getStreamByStreamName(String streamName) {
		
		Stream stream = streamRepository.findByStreamName(streamName);
		
		return stream;

	}
	
	/**
	 * 
	 * @return A list of all stream objects
	 */
	public List<Stream> getAllStreams(){
		
		return streamRepository.findAll(); 	
	}

	/**
	 * 
	 * @return A list of all stream names
	 */
	public List<String> getStreamNames(){
		
		return streamRepository.getStreamNames(); 
	}
	
	/**
	 * Save a stream entity
	 * @param stream
	 */
	public void saveStream(Stream stream) {
		
		streamRepository.save(stream);	
	}
	
	/**
	 * update a stream entity
	 * @param stream
	 */
	public void updateStream(Stream stream){
	
		saveStream(stream);
	}
	
	/**
	 * delete a stream object by given stream id
	 * @param id
	 */
	public void deleteStreamById(int id) {
		
		streamRepository.deleteById(id);
	}
	
	/**
	 * Delete All Stream Objects
	 * @return number of objects deleted
	 */
	public int deleteAllStreams() {
		
		int count = 0;
		List<Stream> streams = streamRepository.findAll();
		
		for(Stream stream : streams) {
			streamRepository.delete(stream);
			count++;
		}
		
		return count;
		
	}
	
}
