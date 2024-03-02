#include "hcp.h"
#include "hospital.h"
#include "illness.h"
#include "patient.h"
#include <algorithm>

using namespace std;

Hospital::Hospital(string nameIn) {
  if (nameIn.empty()){
  	throw std::runtime_error("Name can not be empty");
  }
  name = nameIn;
  map<string,shared_ptr<Health_Care_Provider>> hcps ;
  map<string,weak_ptr<Patient>> patients;

}

bool Hospital::sign_hcp(shared_ptr<Health_Care_Provider> m) {
    auto result = hcps.insert({m->get_name(), m});
    bool returRes = result.second;
    return returRes;
}

bool Hospital::admit_patient(shared_ptr<Patient> m) {
    auto pat = patients.find(m->get_name());
    if (pat == patients.end() || pat->second.expired()) {
        patients[m->get_name()] = m; 
        //std::cout << "The size of the map is: " << patients.size() << std::endl;
        return true;
    }
    return false;  
}

bool Hospital::dismiss_hcp(string n){
	 auto hcp = hcps.find(n);
	 if (hcp != hcps.end()){
	 		hcps.erase(n);
	 		return true;
	 }
	 return false;	 
}

shared_ptr<Health_Care_Provider> Hospital::get_hcp(string n) const {
    auto hcp = hcps.find(n);
    if (hcp != hcps.end()) {
        return hcp->second;
    } 
    throw runtime_error("No Hcp to return");
}

shared_ptr<Patient> Hospital::get_patient(string n) const {
    auto pat = patients.find(n);
    if (pat == patients.end() || pat->second.expired()) {

        throw runtime_error("No patient to return");
    } 
    
    shared_ptr<Patient> res = pat->second.lock();
		return res;
    }

bool Hospital::dismiss_patient(string n){
		auto pat = patients.find(n);
		if (pat != patients.end() && !(pat->second.expired())) {
				patients.erase(n);
				return true;
		}
		return false;
}

std::ostream& operator<<(std::ostream& o, const Hospital& hos) {
    o << "[" << hos.name << ", hcps {" ;
    bool first = true;
    for_each(hos.hcps.begin(), hos.hcps.end(), [&](const std::pair<const std::string&, const std::shared_ptr<Health_Care_Provider>&> each_hcp_pair) {

    
    		if (!first){
    			o << ", ";
    		}
    		first = false;
        o << each_hcp_pair.second->get_name();
    });
    o << "}, patients {" ;
    first = true;
    std::for_each(hos.patients.begin(), hos.patients.end(), [&](const std::pair<const std::string&, const std::weak_ptr<Patient>&> each_patient_pair) {

    		if (!first){
    			o << ", ";
    		}
    		first = false;
        if (auto patientPtr = each_patient_pair.second.lock()) {
        o << patientPtr->get_name(); 
    }
    });
    o << "}]";
    return o;
}


//--------------------Medical_Specialty---------------------------------

std::ostream& operator<<(std::ostream& o, Medical_Specialty m) {
    switch (m) {
        case Medical_Specialty::Cardiology:
            o << "Cardiology";
            break;
        case Medical_Specialty::Dermatology:
            o << "Dermatology";
            break;
        case Medical_Specialty::Endocrinology:
            o << "Endocrinology";
            break;
        case Medical_Specialty::Gynecology:
            o << "Gynecology";
            break;
        case Medical_Specialty::Neurology:
            o << "Neurology";
            break;
        case Medical_Specialty::Oncology:
            o << "Oncology";
            break;
        case Medical_Specialty::Pathology:
            o << "Pathology";
            break;
        case Medical_Specialty::Pediatrics:
            o << "Pediatrics";
            break;
        case Medical_Specialty::Pulmonology:
            o << "Pulmonology";
            break;
        case Medical_Specialty::Urology:
            o << "Urology";
            break;
        default:
            o << "Unknown Specialty";
            break;
    }
    return o;
}





