package verkehrschaostruckcompany;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import verkehrschaos.TTruckListHolder;
import verkehrschaos.Truck;
import verkehrschaos.TruckCompany;
import verkehrschaos.TruckCompanyPOA;

public class TruckCompanyImpl extends TruckCompanyPOA {
	private String name;
	private String location;
	private ArrayList<Truck> trucks_in_depot = new ArrayList<Truck>();
	private ArrayList<Truck> trucks_on_the_road = new ArrayList<Truck>();
	
	public TruckCompanyImpl(String name, String location) {
		super();
		System.out.println("DEBUG: TruckCompanyImpl: TruckCompanyImpl(name, location)");
		this.name = name;
		this.location = location;
	}
	
	public TruckCompanyImpl(String name) {
		super();
		System.out.println("DEBUG: TruckCompanyImpl: TruckCompanyImpl(name)");
		this.name = name;
	}
	
	@Override
	/* Gibt den Namen der Spedition. */
	public String getName() {
		System.out.println("DEBUG: TruckCompanyImpl: getName()");
		return name;
	}

	@Override
	/* Fuegt der Spedition einen LKW hinzu.
     * Damit ist die Spedition die dem LKW zugeordnete Spedition:
     */
	public void addTruck(Truck truck) {
		System.out.println("DEBUG: TruckCompanyImpl: addTruck()");
		trucks_in_depot.add(truck);
		//truck.setCompany((TruckCompany)this);
	}

	@Override
	/* Entfernt den LKW von der Spedition
     */
	public void removeTruck(Truck truck) {
		System.out.println("DEBUG: TruckCompanyImpl: removeTruck()");
		trucks_in_depot.remove(truck);
	}

	@Override
	/* Liefert eine Liste aller verfuegbaren LKWs.
     * Es duerfen nur die LKWs enthalten sein, die einen Fahrauftrag uebernehmen koennen.
     * Rueckgabewert soll die Anzahl dieser LKWs angeben.
     */
	public int getTrucks(TTruckListHolder trucks) {
		System.out.println("DEBUG: TruckCompanyImpl: getTrucks()");
		//trucks.value = (Truck[])this.trucks_in_depot.toArray();
		trucks.value = new Truck[trucks_in_depot.size()];
		
		for(int i = 0; i < trucks_in_depot.size(); i++) {
			trucks.value[i] = trucks_in_depot.get(i);
		}
		
		//return this.trucks_in_depot.size();
		return trucks.value.length;
	}

	@Override
	/* LKW hat Spedition verlassen.
     * Spedition ist nicht mehr fuer den Laster zustaendig, muss aus Liste der Laster entfernt werden.
     * Wird von Streets aufgerufen */
	public void leave(Truck truck) {
		System.out.println("DEBUG: TruckCompanyImpl: leave()");
		removeTruck(truck);		
	}

	@Override
	/* LKW wird angekuendigt.
     * Eine andere Spedition hat einen Laster auf den Weg zu dieser Spedition gebracht.
     * Spedition soll den LKW sofort durch Aufruf von Truck.setCompany uebernehmen.
     * Wird von Streets aufgerufen */
	public void advise(Truck truck) {
		System.out.println("DEBUG: TruckCompanyImpl: advise()");
		truck.setCompany((TruckCompany)this);
		trucks_on_the_road.add(truck);
	}

	@Override
	 /* LKW ist im Ziel angekommen.
     * Steht nun fuer neue Fahrten zur Verfuegung.
     * Wird von Streets aufgerufen */
	public void arrive(Truck truck) {
		System.out.println("DEBUG: TruckCompanyImpl: arrive()");
		trucks_on_the_road.remove(truck);
		addTruck(truck);
	}

	@Override
	/*
     * Stilllegung der Spedition (TruckCompany Anwendung wird beendet).
     * Wird von der Steueranwendung (Client) aufgerufen.
     * Legt auch alle zugeordneten LKWs still.
     * Beenden der Anwendung durch Aufruf von orb.shutdown(true).
     * Nach orb.shutdown kleine Pause einlegen (0.5 sec) um Exception zu vermeiden.
     */
	public void putOutOfService() {
		System.out.println("DEBUG: TruckCompanyImpl: outOutOfService()");
		System.exit(0);
	}

}
