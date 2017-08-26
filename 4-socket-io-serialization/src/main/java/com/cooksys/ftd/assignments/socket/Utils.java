package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     * @throws JAXBException 
     */
    public static JAXBContext createJAXBContext() throws JAXBException{
		return JAXBContext.newInstance(Config.class, Student.class);
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     * @throws JAXBException 
     * @throws IOException 
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
    	try {
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
		    File configXML = new File(configFilePath);
	
		    if(configXML.createNewFile()) {
			    Utils.createDefaultConfigFile(configXML, jaxb);
		    }
		    
			return (Config) jaxbUnmarshaller.unmarshal(configXML);
    	} catch (JAXBException jaxbException) {
			System.out.println("Error with JAXB in loadConfig() method: ");
			jaxbException.printStackTrace();
			System.exit(1);
			return null;
    	} catch (IOException ioException) {
			System.out.println("Error with operations on config file in loadConfig() method: ");
			ioException.printStackTrace();
			System.exit(1);
			return null;
		}
		
    }

	private static void createDefaultConfigFile(File configXML, JAXBContext jaxb) {
		try {
			Marshaller jaxbMarshaller = jaxb.createMarshaller();
	    	
	    	Config defaultConfig = new Config();
	    	LocalConfig defaultLocalConfig = new LocalConfig();
	    	RemoteConfig defaultRemoteConfig = new RemoteConfig();
	    	
	    	defaultLocalConfig.setPort(1337);
	    	
	    	defaultRemoteConfig.setHost(InetAddress.getLocalHost().getHostAddress());
	    	defaultRemoteConfig.setPort(1337);
	    	
	    	defaultConfig.setStudentFilePath("config/student.xml");
	    	defaultConfig.setLocal(defaultLocalConfig);
	    	defaultConfig.setRemote(defaultRemoteConfig);
	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(defaultConfig, configXML);
    		System.out.println("\nNo config found! Default config created: ");
			jaxbMarshaller.marshal(defaultConfig, System.out);
		} catch (JAXBException jaxbException) {
			System.out.println("Error with JAXB in createDefaultConfigFile() method: ");
			jaxbException.printStackTrace();
			System.exit(1);
		} catch (UnknownHostException unknownHostException) {
			System.out.println("Error with host name retrieved via InetAddress.getLocalHost() in createDefaultConfigFile() method: ");
			unknownHostException.printStackTrace();
			System.exit(1);
		}
	}

	public static void updateConfigIPAddress(String configFilePath, JAXBContext jaxb) {
		try {
			Marshaller jaxbMarshaller = jaxb.createMarshaller();
	    	Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
	
		    File configXML = new File(configFilePath);
			
		    Config config = (Config) jaxbUnmarshaller.unmarshal(configXML);
		    
	    	RemoteConfig defaultRemoteConfig = new RemoteConfig();
	    	
	    	defaultRemoteConfig.setHost(InetAddress.getLocalHost().getHostAddress());
	    	defaultRemoteConfig.setPort(config.getRemote().getPort());
	    	
	    	config.setRemote(defaultRemoteConfig);
	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(config, configXML);
    		System.out.println("Config with updates IP Address:");
			jaxbMarshaller.marshal(config, System.out);
    		System.out.println("\nResuming attempt to connect to server\n");
		} catch (JAXBException jaxbException) {
			System.out.println("Error with JAXB in updateConfigIPAddress() method: ");
			jaxbException.printStackTrace();
			System.exit(1);
		} catch (UnknownHostException unknownHostException) {
			System.out.println("Error with host name retrieved via InetAddress.getLocalHost() in updateConfigIPAddress() method: ");
			unknownHostException.printStackTrace();
			System.exit(1);
		}
	}
}
