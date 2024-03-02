#ifndef PATIENT_H
#define PATIENT_H

#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <map>
#include <memory>

using namespace std;

// Forward declaration
class Illness;
class Hospital;
class Hcp;
enum class Medical_Specialty;

class Patient {
private:
    std::string name ; 
		int age ;  
		std::set<Illness> illnesses; 
    unsigned wealth; 
public:
    Patient(std::string name, int age, const std::set<Illness>& illnesses, unsigned wealth = 200);
    void increase_wealth(unsigned x);
    bool pay_procedure(unsigned x);
    void catch_disease(const Illness& x) ;
    bool requires_treatment_in(Medical_Specialty x) const;
    bool healthy() const;
    std::string get_name() const;
    unsigned cure(Medical_Specialty x);
    
    
    friend std::ostream& operator<<(std::ostream& o, const Patient& p);
    friend bool operator==(const Patient& a, const Patient& b);
};




#endif //PATIENT_H

