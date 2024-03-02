#include "hcp.h"
#include "hospital.h"
#include "illness.h"
#include "patient.h"
#include <algorithm>

using namespace std;

Health_Care_Provider::Health_Care_Provider(string nameIn, const set<Medical_Specialty>& topicsIn, unsigned wealthIn){
	 if (nameIn.empty()){
  	throw std::runtime_error("Illness name must not be empty");
  }
  name = nameIn;
  topics = topicsIn;
  wealth = wealthIn;
}

string Health_Care_Provider::get_name() const{
	return name;
}

void Health_Care_Provider::increase_wealth(unsigned x){
	if (x <= 0){
  	throw std::runtime_error("can not increase by a negative value");
  }
  wealth += x;
}
bool Health_Care_Provider::pay_license(unsigned x){
	if (wealth >= x){
		wealth -= x;
		return true;
	}
	return false;
}

bool Health_Care_Provider::eligible_for(Medical_Specialty m){
	return any_of(topics.begin(), topics.end(), [&](const Medical_Specialty& eacht) {
        return eacht == m;
    });
}

void Health_Care_Provider::receive_license(Medical_Specialty x){
	topics.insert(x);
}

std::ostream& operator<<(std::ostream& o, const Health_Care_Provider& hcp) {
    o << "[" << hcp.name << ", {";
    bool first = true;
   
    for_each(hcp.topics.begin(), hcp.topics.end(), [&](const Medical_Specialty& eacht) {
    		if (!first){
    			o << ", ";
    		}
    		first = false;
        o << eacht;
    });
    o << "}, " << hcp.wealth << " moneyz, "  << hcp.hcp_type() << "]";
    
    return o;
}

// -------------------- Teaching ------------------------
 
Teaching_Health_Care_Provider::Teaching_Health_Care_Provider(unsigned feeIn, std::string nameIn, const std::set<Medical_Specialty>& topicsIn, unsigned wealthIn): Health_Care_Provider(nameIn, topicsIn, wealthIn){
	if (feeIn <= 0){
  	throw std::runtime_error("No negativ teaching fees alloud!");
  }
  fee = feeIn;
}

unsigned Teaching_Health_Care_Provider::perform_procedure(unsigned x, std::shared_ptr<Patient> p, Medical_Specialty m) {
    unsigned res = 0;
    if (this->eligible_for(m) && p->requires_treatment_in(m) && p->pay_procedure(x)) {
        res = p->cure(m);
        this->increase_wealth(x);
    }    
    return res;  
}

string Teaching_Health_Care_Provider::hcp_type() const{
	return "Teacher";
}



bool Teaching_Health_Care_Provider::teach(Medical_Specialty m,shared_ptr<Health_Care_Provider> target){

	if (target->get_name() == this->get_name() || !this->eligible_for(m)  || target->eligible_for(m) || !(target->pay_license(fee))){
		return false;
	}
	//target hat in der if abfrage schon gezahlt falls möglich!!!
	this->increase_wealth(fee);
	target->receive_license(m);
	return true;
}


// ------ Smart ----------------------


Smart_Health_Care_Provider::Smart_Health_Care_Provider(unsigned feeIn, std::string nameIn, const std::set<Medical_Specialty>& topicsIn, unsigned wealthIn): Health_Care_Provider(nameIn, topicsIn, wealthIn){
	if (feeIn <= 0){
  	throw std::runtime_error("No negativ teaching fees alloud!");
  }
  fee = feeIn;
}

unsigned Smart_Health_Care_Provider::perform_procedure(unsigned x, std::shared_ptr<Patient> p, Medical_Specialty m) {
    return 0;  
}

string Smart_Health_Care_Provider::hcp_type() const{
	return "Smart";
}



void Smart_Health_Care_Provider::receive_license(Medical_Specialty m){
	this->Health_Care_Provider::receive_license(m);
	this->increase_wealth(fee);
}





































