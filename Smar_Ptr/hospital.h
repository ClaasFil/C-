#ifndef HOSPITAL_H
#define HOSPITAL_H

#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <map>
#include <memory>

using namespace std;

// Forward declaration
class Illness;
class Patient;
class Hcp;

class Hospital {
private:
    string name;// Name of hospital.
    map<string,shared_ptr<Health_Care_Provider>> hcps ;//Key is the name of a HCP.
    map<string,weak_ptr<Patient>> patients;// Key is the name of a Patient.

public:
    Hospital(std::string name);
    bool sign_hcp(shared_ptr<Health_Care_Provider> m);
    bool admit_patient(shared_ptr<Patient> m);
    bool dismiss_hcp(string n);
    shared_ptr<Health_Care_Provider> get_hcp(string n) const;
    shared_ptr<Patient> get_patient(string n) const;
    bool dismiss_patient(string n);
    
    friend std::ostream& operator<<(std::ostream& o, const Hospital& h);
};

















enum class Medical_Specialty {
    Cardiology, Dermatology, Endocrinology, Gynecology,
    Neurology, Oncology, Pathology, Pediatrics, Pulmonology, Urology
};

std::ostream& operator<<(std::ostream& o, Medical_Specialty m);






#endif //PATIENT_H
