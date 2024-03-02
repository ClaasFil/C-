#include "hcp.h"
#include "hospital.h"
#include "illness.h"
#include "patient.h"
#include <set>
#include <algorithm>


using namespace std;



Patient::Patient(std::string nameIn, int ageIn, const std::set<Illness>& illnessesIn, unsigned wealthIn ) {
    if (nameIn.empty()){
  		throw std::runtime_error("Patient name must not be empty");
  } 
  if (ageIn < 0){
  		throw std::runtime_error("Patient age must not be smaler 0");
  }
  if (illnessesIn.size() == 0){
  		throw std::runtime_error("Patiet must hae atleast one Illnes to be in a Hospital");
  }

  
  name = nameIn;
  age = ageIn;
  illnesses = illnessesIn;
  wealth = wealthIn;
}

void Patient::increase_wealth(unsigned x){
	if (x < 0){
  		throw std::runtime_error("You can not increase with negativ amount");
	}
	wealth += x;
}

bool Patient::pay_procedure(unsigned x){
	if (x <= wealth){
	wealth -= x;
	return true;
	}
	return false;

}

void Patient::catch_disease(const Illness& x) {
	illnesses.insert(x);
}

bool Patient::requires_treatment_in(Medical_Specialty x) const {
    return any_of(illnesses.begin(), illnesses.end(), [&](const Illness& eachillness) {
        return eachillness.get_specialty() == x;
    });
}

bool Patient::healthy() const{
	return illnesses.size() == 0;
}

string Patient::get_name() const{
	return name;
}

unsigned Patient::cure(Medical_Specialty x) {
    unsigned count = 0;
    for (auto it = illnesses.begin(); it != illnesses.end(); ) {
        if (it->get_specialty() == x) {
            it = illnesses.erase(it);  
            count++;
        } else {
            ++it;  
        }
    }
    return count;
}


bool operator==(const Patient& a, const Patient& b) {
    return a.name == b.name &&a.age == b.age &&a.illnesses == b.illnesses &&a.wealth == b.wealth ;
}


std::ostream& operator<<(std::ostream& o, const Patient& pat) {
    o << "[" << pat.name << ", " << pat.age << " years, {";
    bool first = true;
    for_each(pat.illnesses.begin(), pat.illnesses.end(), [&](Illness eachillness) {
    		if (!first){
    			o << ", ";
    		}
    		first = false;
        o << eachillness.get_name();
    });
    o << "}, " << pat.wealth << " moneyz]";
    
    return o;
}



