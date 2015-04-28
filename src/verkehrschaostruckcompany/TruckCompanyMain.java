package verkehrschaostruckcompany;

import java.util.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import verkehrschaos.*;

public class TruckCompanyMain {
	public static void main(String[] args) {
		try {
			// ORB Eigenschaften setzen
			Properties props = new Properties();
			props.put("org.omg.CORBA.ORBInitialPort", args[1]);
			props.put("org.omg.CORBA.ORBInitialHost", args[2]);
			ORB orb = ORB.init(args, props);

			// Referenz zum rootPOA holen und POA Manager aktivieren
			POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootPoa.the_POAManager().activate();
			
			// Erzeuge Servant
			TruckCompanyImpl truckCompany = new TruckCompanyImpl(args[3], args[4]);
			
			// Objekt Referenz des Servants holen
			org.omg.CORBA.Object ref = rootPoa.servant_to_reference(truckCompany);
			
			// Downcast Corba-Objekt -> TruckCompany
			TruckCompany href = TruckCompanyHelper.narrow(ref);
			
			// Referenz zum Namensdienst (root naming context holen)
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			
			// Verwendung von NamingContextExt, ist Teil der Interoperable
			// Naming Sevice (INS) Spezifikation.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			// binde die Objekt Referenz an einen Namen
			String name = args[3];
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			
			// Referenz auf streets vom Namserver holen
			Streets streets = StreetsHelper.narrow(ncRef.resolve_str(args[0]));
			
			// TruckCompany bei Streets an vorgegebenen Platz anmelden
			streets.claim(href, args[4]);
			
			System.out.println("TruckCompany ready and waiting....");
			
			// Orb starten
			orb.run();

		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		
		System.out.println("TruckCompany Exiting ...");
	}
}
