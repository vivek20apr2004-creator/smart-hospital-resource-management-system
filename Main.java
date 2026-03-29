import java.util.*;

class Patient {
    int id;
    String name;
    int severity;

    Patient(int id, String name, int severity) {
        this.id = id;
        this.name = name;
        this.severity = severity;
    }
}

class Doctor {
    int id;
    String name;
    String specialization;

    Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }
}

class Bed {
    int id;
    boolean occupied;

    Bed(int id) {
        this.id = id;
        this.occupied = false;
    }
}

class PatientComparator implements Comparator<Patient> {
    public int compare(Patient a, Patient b) {
        return b.severity - a.severity;
    }
}

class HospitalSystem {

    private ArrayList<Patient> patients = new ArrayList<>();
    private HashMap<Integer, Doctor> doctors = new HashMap<>();
    private ArrayList<Bed> beds = new ArrayList<>();

    private PriorityQueue<Patient> emergencyQueue =
            new PriorityQueue<>(new PatientComparator());

    private LinkedList<Patient> waitingList = new LinkedList<>();

    void addDoctor(int id, String name, String spec) {
        doctors.put(id, new Doctor(id, name, spec));
    }

    void addBed(int id) {
        beds.add(new Bed(id));
    }

    void addPatient(int id, String name, int severity) {
        Patient p = new Patient(id, name, severity);
        patients.add(p);

        if (severity == 3) {
            emergencyQueue.add(p);
            System.out.println("Emergency patient added");
        } else {
            waitingList.add(p);
            System.out.println("Patient added to waiting list");
        }
    }

    void assignBed() {
        for (Bed b : beds) {
            if (!b.occupied) {
                b.occupied = true;
                System.out.println("Bed " + b.id + " assigned");
                return;
            }
        }
        System.out.println("No beds available");
    }

    void treatPatient() {
        if (!emergencyQueue.isEmpty()) {
            Patient p = emergencyQueue.poll();
            System.out.println("Treating CRITICAL patient: " + p.name);
        } else if (!waitingList.isEmpty()) {
            Patient p = waitingList.poll();
            System.out.println("Treating NORMAL patient: " + p.name);
        } else {
            System.out.println("No patients available");
        }
    }

    void showPatients() {
        for (Patient p : patients) {
            System.out.println(p.id + " " + p.name + " Severity: " + p.severity);
        }
    }

    void findDoctor(int id) {
        if (doctors.containsKey(id)) {
            Doctor d = doctors.get(id);
            System.out.println("Doctor Found: " + d.name + " (" + d.specialization + ")");
        } else {
            System.out.println("Doctor not found");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HospitalSystem hs = new HospitalSystem();

        hs.addDoctor(1, "Dr Sharma", "Cardiology");
        hs.addDoctor(2, "Dr Khan", "Neurology");
        hs.addBed(101);
        hs.addBed(102);

        int choice;

        do {
            System.out.println("\n--- Hospital System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Treat Patient");
            System.out.println("3. Assign Bed");
            System.out.println("4. Show Patients");
            System.out.println("5. Find Doctor");
            System.out.println("0. Exit");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Severity (1-3): ");
                    int severity = sc.nextInt();
                    hs.addPatient(id, name, severity);
                    break;

                case 2:
                    hs.treatPatient();
                    break;

                case 3:
                    hs.assignBed();
                    break;

                case 4:
                    hs.showPatients();
                    break;

                case 5:
                    System.out.print("Enter Doctor ID: ");
                    int did = sc.nextInt();
                    hs.findDoctor(did);
                    break;
            }

        } while (choice != 0);

        sc.close();
    }
}