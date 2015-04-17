package verkehrschaostruckcompany;

import java.util.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

import verkehrschaos.*;

public class TruckCompanyMain {
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.put("org.omg.CORBA.ORBInitialPort", args[0]);
			props.put("org.omg.CORBA.ORBInitialHost", args[1]);
			ORB orb = ORB.init(args, props);

			POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPoa.the_POAManager().activate();
			
			
			/*Streets vom NameService holen, damit ich meinen Truck erstellen kann*/
			NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
			org.omg.CORBA.Object obj = nc.resolve_str(args[4]);
			
			Streets streets = StreetsHelper.narrow(obj);
			
			/*Erstellung meiner Company mit Name der Spedition und location*/
			TruckCompanyPOA truckCompany = new TruckCompanyImpl(args[2]);
						
			/*Objektref holen*/
			org.omg.CORBA.Object ref = rootPoa.servant_to_reference(truckCompany);
			TruckCompany href = TruckCompanyHelper.narrow(ref);
			
			//an einem der vorgegebenen Platz ansiedeln
			streets.claim(href, args[3]);
			
			/*F�r Namensdienst Namen vergeben und rebinden*/
			String name = args[2];
			NameComponent path[] = nc.to_name(name);
			nc.rebind(path, href);
			
			System.out.println("Company ready and waiting....");
			
			orb.run();

		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("TruckCompany Exiting ...");
	}
}
