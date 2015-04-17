package verkehrschaostruckcompany;

import java.util.Set;
import java.util.TreeSet;

import verkehrschaos.TTruckListHolder;
import verkehrschaos.Truck;
import verkehrschaos.TruckCompany;
import verkehrschaos.TruckCompanyPOA;

public class TruckCompanyImpl extends TruckCompanyPOA {
	private String name;
	private Set<Truck> trucks_in_depot = new TreeSet<Truck>();
	private Set<Truck> trucks_on_the_road = new TreeSet<Truck>();
	
	public TruckCompanyImpl(String name) {
		super();
		this.name = name;
	}
	
	@Override
	/* Gibt den Namen der Spedition. */
	public String getName() {
		return name;
	}

	@Override
	/* Fuegt der Spedition einen LKW hinzu.
     * Damit ist die Spedition die dem LKW zugeordnete Spedition:
     */
	public void addTruck(Truck truck) {
		trucks_in_depot.add(truck);
	}

	@Override
	/* Entfernt den LKW von der Spedition
     */
	public void removeTruck(Truck truck) {
		trucks_in_depot.remove(truck);
	}

	@Override
	/* Liefert eine Liste aller verfuegbaren LKWs.
     * Es duerfen nur die LKWs enthalten sein, die einen Fahrauftrag uebernehmen koennen.
     * Rueckgabewert soll die Anzahl dieser LKWs angeben.
     */
	public int getTrucks(TTruckListHolder trucks) {
		trucks.value = (Truck[])this.trucks_in_depot.toArray();
		
		return this.trucks_in_depot.size();
	}

	@Override
	/* LKW hat Spedition verlassen.
     * Spedition ist nicht mehr fuer den Laster zustaendig, muss aus Liste der Laster entfernt werden.
     * Wird von Streets aufgerufen */
	public void leave(Truck truck) {
		trucks_in_depot.remove(truck);
		trucks_on_the_road.add(truck);
	}

	@Override
	/* LKW wird angekuendigt.
     * Eine andere Spedition hat einen Laster auf den Weg zu dieser Spedition gebracht.
     * Spedition soll den LKW sofort durch Aufruf von Truck.setCompany uebernehmen.
     * Wird von Streets aufgerufen */
	public void advise(Truck truck) {
		truck.setCompany((TruckCompany)this);
	}

	@Override
	 /* LKW ist im Ziel angekommen.
     * Steht nun fuer neue Fahrten zur Verfuegung.
     * Wird von Streets aufgerufen */
	public void arrive(Truck truck) {
		trucks_on_the_road.remove(truck);
		trucks_in_depot.add(truck);
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
		System.exit(0);
	}

}
