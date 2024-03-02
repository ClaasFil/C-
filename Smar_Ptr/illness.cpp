#include "hcp.h"
#include "hospital.h"
#include "illness.h"
#include "patient.h"
#include <algorithm>

using namespace std;

Illness::Illness(Medical_Specialty medIn, const std::string& nameIn){

  if (nameIn.empty()){
  	throw std::runtime_error("Illness name must not be empty");
  }
  med = medIn;
  name = nameIn;
}
Medical_Specialty Illness::get_specialty() const {
	return med;
}

std::string Illness::get_name() const {
	return name;
}

bool operator==(const Illness& a, const Illness& b) {
    return a.get_specialty() == b.get_specialty() && a.get_name() == b.get_name();
}

std::ostream& operator<<(std::ostream& o, const Illness& ill) {
    o << "[" << ill.get_specialty() << ", " << ill.get_name() << "]";
    return o;
}

bool operator<(const Illness& a, const Illness& b){
	if (a.get_specialty() < b.get_specialty()){return true;}
	if (a.get_specialty() > b.get_specialty()){return false;}
	return a.get_name() < b.get_name();
}


