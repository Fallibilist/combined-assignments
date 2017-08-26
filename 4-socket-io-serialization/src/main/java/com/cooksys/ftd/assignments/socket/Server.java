package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     * @throws IOException 
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
    	try {
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
		    File studentXML = new File(studentFilePath);
	
		    if(studentXML.createNewFile()) {
		    	Marshaller jaxbMarshaller = jaxb.createMarshaller();
		    	
		    	Student defaultStudent = new Student();
	
		    	defaultStudent.setFirstName("Greg");
		    	defaultStudent.setLastName("Hill");
		    	defaultStudent.setFavoriteIDE("Eclipse");
		    	defaultStudent.setFavoriteLanguage("Java");
		    	defaultStudent.setFavoriteParadigm("Object-Oriented");
	
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    		jaxbMarshaller.marshal(defaultStudent, studentXML);
	    		System.out.println("\nNo students found! Default student created: ");
	    		jaxbMarshaller.marshal(defaultStudent, System.out);
		    }
		    
			return (Student) jaxbUnmarshaller.unmarshal(studentXML);
		} catch (JAXBException jaxbException) {
			System.out.println("Error with JAXB in loadStudent() method: ");
			jaxbException.printStackTrace();
			System.exit(1);
			return null;
		} catch (IOException ioException) {
			System.out.println("Error with operations on student file in loadStudent() method: ");
			ioException.printStackTrace();
			System.exit(1);
			return null;
		}
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) {
    	JAXBContext jaxb;
    	Config config;
    	LocalConfig localConfig;
    	
    	try {
			jaxb = Utils.createJAXBContext();
		} catch (JAXBException e2) {
			jaxb = null;
			System.out.println("Error creating JAXBContext with Config and Student classes! You're out of luck!");
			System.exit(1);
		}
    	
    	while(true) {
		config = Utils.loadConfig("config/config.xml", jaxb);
		localConfig = config.getLocal();
		if(localConfig != null) {
	    	try (ServerSocket serverSocket = new ServerSocket(localConfig.getPort());
		    		Socket client = serverSocket.accept();
	    			OutputStream outputStream = client.getOutputStream();
	    	){
	    		Student student = Server.loadStudent(config.getStudentFilePath(), jaxb);
	    		Marshaller jaxbMarshaller = jaxb.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    		jaxbMarshaller.marshal(student, outputStream);
			} catch (BindException tooManyServers) {
				System.out.println("Server already running!");
				System.exit(0);
			} catch (IOException ioException) {
				System.out.println("Error with operations on socket server side: ");
				ioException.printStackTrace();
				System.exit(1);
			}  catch (JAXBException jaxbException) {
				System.out.println("Error with JAXB on server: ");
				jaxbException.printStackTrace();
				System.exit(1);
			}
		} else {
			System.out.println("null");
		}
    	}
    }
}
