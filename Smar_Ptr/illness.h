#ifndef ILLNESS_H
#define ILLNESS_H

#include <iostream>
#include <string>
#include <vector>
#include <set>
#include <map>
#include <memory>

using namespace std;

// Forward declaration
class Patient;
class Hospital;
class Hcp;
enum class Medical_Specialty;

class Illness {
private:
    Medical_Specialty med; //Describes which specialty is required to cure this illness
		std::string name; //Specific name of the illness
public:
    Illness(Medical_Specialty,const std::string&);
    Medical_Specialty get_specialty() const;
    std::string get_name() const;
    
    friend std::ostream& operator<<(std::ostream& o, const Illness& ill);
    friend bool operator==(const Illness& a, const Illness& b);
    friend bool operator<(const Illness& a, const Illness& b);
};

#endif //ILLNES_H
