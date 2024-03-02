#ifndef HCP_H
#define HCP_H

#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <map>
#include <memory>

//g++ -g -o test_program test.cpp patient.cpp hospital.cpp illness.cpp hcp.cpp -std=c++17
/*
gedit hcp.cpp
gedit hospital.h
gedit hospital.cpp
gedit illness.h
gedit illness.cpp
gedit patient.h
gedit patient.cpp
gedit test.cpp
g++ -g -o test_program test.cpp patient.cpp hospital.cpp illness.cpp hcp.cpp -std=c++17
./test_program
*/

using namespace std;

// Forward declaration
class Illness;
class Hospital;
class Patient;
enum class Medical_Specialty;



class Health_Care_Provider {
private:
    string name;  //Name of health care provider (HCP).
    set<Medical_Specialty> topics;  // Set of topics in which HCP is proficient in
    unsigned wealth;  // Wealth of HCP.
public:
    Health_Care_Provider(string n, const set<Medical_Specialty>& topics, unsigned wealth=500);
    string get_name() const;
    virtual ~Health_Care_Provider() = default;
    void increase_wealth(unsigned x);
    virtual unsigned perform_procedure(unsigned,shared_ptr<Patient>, Medical_Specialty) {return 0;};
    virtual string hcp_type() const {return "";};
    bool pay_license(unsigned x) ;
    virtual void receive_license(Medical_Specialty x);
    bool eligible_for(Medical_Specialty m);
    
    friend std::ostream& operator<<(std::ostream& o, const Health_Care_Provider& h);
};


class Teaching_Health_Care_Provider : public Health_Care_Provider {
private:
    unsigned fee;
public:
    Teaching_Health_Care_Provider(unsigned int fee, std::string name,
                                  const std::set<Medical_Specialty>& topics,
                                  unsigned int wealth);
    unsigned perform_procedure(unsigned x, std::shared_ptr<Patient> p, Medical_Specialty m) override;
    std::string hcp_type() const override;
    bool teach(Medical_Specialty m, std::shared_ptr<Health_Care_Provider> target);
};

class Smart_Health_Care_Provider : public Health_Care_Provider {
private:
    unsigned fee;
public:
    Smart_Health_Care_Provider(unsigned int fee, std::string name,
                                  const std::set<Medical_Specialty>& topics,
                                  unsigned int wealth);
    unsigned perform_procedure(unsigned x, std::shared_ptr<Patient> p, Medical_Specialty m) override;
    std::string hcp_type() const override;
    void receive_license(Medical_Specialty m);
};

/*
public:
    Teaching_Health_Care_Provider(unsigned fee, std::string name, const std::set<Medical_Specialty>& topics, unsigned wealth = 500): Health_Care_Provider(name, topics, wealth){};
    unsigned perform_procedure(unsigned x, std::shared_ptr<Patient> p, Medical_Specialty m) override;
    std::string hcp_type() const override;
    bool teach(Medical_Specialty m, std::shared_ptr<Health_Care_Provider> target);
};
*/







#endif //PATIENT_H
