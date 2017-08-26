package com.cooksys.ftd.assignments.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
    	JAXBContext jaxb;
    	RemoteConfig remoteConfig;
    	
    	try {
			jaxb = Utils.createJAXBContext();
		} catch (JAXBException jaxbException) {
			System.out.println("Error creating JAXBContext with Config and Student classes: ");
			jaxbException.printStackTrace();
			System.exit(1);
			jaxb = null;
		}
    	
		remoteConfig = Utils.loadConfig("config/config.xml", jaxb).getRemote();
		
	    try(Socket client = new Socket();){
	    	client.connect(new InetSocketAddress(remoteConfig.getHost(), remoteConfig.getPort()), 1000);
    		InputStream inputStream = client.getInputStream();
	    	
	    	Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
			
	    	Student student = (Student) jaxbUnmarshaller.unmarshal(inputStream);
	    	System.out.println(student.toString());
	    	
	    	inputStream.close();
		} catch (SocketTimeoutException socketTimedOut) {
			System.out.println("Incorrect IPv4 Address found in config file. Updating config file!\n");
			Utils.updateConfigIPAddress("config/config.xml", jaxb);
			main(args);
		} catch (IOException ioException) {
			System.out.println("Error with operations on socket client side: ");
			ioException.printStackTrace();
			System.exit(1);
		} catch (JAXBException jaxbException) {
			System.out.println("Error with JAXB on client: ");
			jaxbException.printStackTrace();
			System.exit(1);
		} 
    }
}
