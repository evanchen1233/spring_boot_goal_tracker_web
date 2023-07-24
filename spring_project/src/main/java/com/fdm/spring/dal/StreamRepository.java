package com.fdm.spring.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.spring.model.Stream;

/**
 * Spring data repository interface for class Stream
 * @author Yifeng Chen
 *
 */
@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer>
{
	/**
	 * 
	 * @return A list of all stream names
	 */
	@Query("SELECT streamName FROM Stream")
	List<String> getStreamNames();
	
	/**
	 * 
	 * @param streamName stream's name
	 * @return Stream onject by given stream name
	 */
	@Query("FROM Stream s WHERE s.streamName = :streamName")
	Stream findByStreamName(@Param("streamName") String streamName);
}
