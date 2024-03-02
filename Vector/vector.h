#ifndef VECTOR_H
#define VECTOR_H

#include <initializer_list>
#include <iostream>
#include <stdexcept>

// Custom exception for an empty Vector
class VectorEmptyException : public std::runtime_error {
public:
    VectorEmptyException() : std::runtime_error("Vector is empty") {}
};

// Custom exception for an index out of bounds
class VectorIndexOutOfBoundsException : public std::out_of_range {
public:
    VectorIndexOutOfBoundsException() : std::out_of_range("Vector index out of bounds") {}
};

// Custom exception for an index out of bounds
class VectorSizeNegativException: public std::out_of_range {
public:
    VectorSizeNegativException() : std::out_of_range("Vector size must be non-negative") {}
};

// Custom exception for an index out of bounds
class IteratorPointingToZeroException: public std::out_of_range {
public:
    IteratorPointingToZeroException() : std::out_of_range("An Iterator without end can not be extendet") {}
};

//Custom exception for an index out of bounds
class IteratorExtendetOverEndException: public std::out_of_range {
public:
    IteratorExtendetOverEndException() : std::out_of_range("Trying to extend the iterator over his end") {}
};

//Custom exception for an index out of bounds
class DereferencingNullPtrException: public std::out_of_range {
public:
    DereferencingNullPtrException() : std::out_of_range("Do not try to dereference a nullpointer") {}
};

//Custom exception for an index out of bounds
class ArrowToNullPtrException: public std::out_of_range {
public:
    ArrowToNullPtrException() : std::out_of_range("Do not try to dereference a nullpointer") {}
};
//Custom exception for an index out of bounds
class DereferencingIteratorOutOfBounceException: public std::out_of_range {
public:
    DereferencingIteratorOutOfBounceException() : std::out_of_range("Do not try to dereference a iterator which is out of bounce") {}
};


template <typename T>
class Vector {

public:
  class ConstIterator;
  class Iterator;
  class IteratorX;
  using value_type = T;
  using size_type = std::size_t;
  using difference_type = std::ptrdiff_t;
  using reference = value_type&;
  using const_reference = const value_type&;
  using pointer = value_type*;
  using const_pointer = const value_type*;
  using iteratorX = Vector::IteratorX;
  using iterator = Vector::Iterator;
  using const_iterator = Vector::ConstIterator;

private:
    size_type sz;         // Number of elements stored in the Vector
    size_type max_sz;     // Maximum possible number of elements
    static const size_type min_capacity = 5;     // Maximum possible number of elements
    value_type* values;    // Pointer to the array where elements are dynamically stored

public:
    // Constructor: Create an empty Vector with an optional initial size (default is 10)
    Vector(size_type n) {
    		if ( n < 0){
    			throw VectorSizeNegativException();
    		}
        sz = 0;
        max_sz = n;
        values = new value_type[max_sz];
    }

    // Default constructor: Create an empty Vector with a default initial size of 10
    Vector(): Vector(min_capacity) {}

    // Copy constructor: Initialize the Vector using an initializer list
    Vector(std::initializer_list<value_type> li) {
        sz = 0;
        max_sz = li.size();
        values = new value_type[max_sz];
        for (const auto& x : li) {
            values[sz++] = x;
        }
    }

    // Copy constructor: Create a Vector as a copy of another Vector
    Vector(const Vector& x) {
        sz = x.sz;
        max_sz = x.max_sz;
        values = new value_type[max_sz];
        for (size_type i = 0; i < sz; i++) {
            values[i] = x.values[i];
        }
    }

    // Destructor: Clean up dynamically allocated memory
    ~Vector() {
        delete[] values;
    }

    // Assignment operator   nicht selber gefordert in der aufgabenstellung
    Vector& operator=(Vector x) {
        std::swap(sz, x.sz);
        std::swap(max_sz, x.max_sz);
        std::swap(values, x.values);
        return *this;
    }
    
    // Reduce max_sz to match the current size (sz)
    void shrink_to_fit() {
        max_sz = sz;
    }

    // Remove the last element from the Vector
    void pop_back() {
        if (sz == 0) {
            throw VectorEmptyException();
        }
        value_type* newValues = new value_type[max_sz];
        for (size_type i = 0; i < sz - 1; i++) {
            newValues[i] = values[i]; // Make a copy of the elements
        }
        sz--;
        delete[] values;
        values = newValues;
    }

    // Add an element to the end of the Vector
    void push_back(value_type x) {
        if (sz == max_sz) {
            reserve(sz * 2 + 1 );
        }
        values[sz] = x; // Add a copy of 'x' to the end of the 'values' array
        sz++; // Increment the size
    }

    // Reserve memory for n elements in the Vector
    void reserve(size_type n) {
        if (n <= max_sz) {
            return;
        }
        value_type* newValues = new value_type[n];
        for (size_type i = 0; i < sz; i++) {
            newValues[i] = values[i]; // Make a copy of the elements
        }
        delete[] values;
        values = newValues;
        max_sz = n;
    }

    // Access an element by index (non-const and const versions)
     reference operator[](size_type index) {  //& returns a reference of the instance without & it would return a copy
        if (0 <= index && index < sz) {
            return values[index];
        } else {
            throw VectorIndexOutOfBoundsException();
        }
    }

    const_reference operator[](size_type index) const {  //return and reference with only read acces
    																											// first const means retunr value is const secound means that function can be called on const instances
        if (0 <= index && index < sz) {
            return values[index];
        } else {
            throw VectorIndexOutOfBoundsException();
        }
    }

    // Return the number of elements in the Vector
    size_type size() const {   //can be called on const and not const vector bc does not change the vector
        return sz;
    }

    // Clear the Vector (set size to 0)
    void clear() { //nicht const weil es vector veränder
        sz = 0;
        delete[] values; // Deallocate the old array
        values = new value_type[max_sz];
    }

    // Check if the Vector is empty
    bool empty() const {
        return sz == 0;
    }

    // Return the maximum possible number of elements
    size_type capacity() const {
        return max_sz;
    }
    iteratorX begin_x(){
      //std::cout << "Xbegin: " << sz<< std::endl;
    	if(this -> empty()){
    		return  this->end_x();
    	}
    	return IteratorX(values  + sz - 1, values - 1);
    }
  
   iteratorX end_x(){
    	return IteratorX(values -1, values-1 );
    }
    
    iterator begin(){
    	if(this -> empty()){
    		return  this->end();
    	}
    	return Iterator(values, values +sz);
    }
    iterator end(){
    	return Iterator(values+sz, values +sz);
    }
    ConstIterator begin() const {
		  if(this -> empty()){
		  		return  this->end();
		  	}
    	return ConstIterator(values, values +sz);
    }
    ConstIterator end() const {
    	return ConstIterator(values+sz, values +sz);
    }
    
    
    iterator insert(const_iterator pos, const_reference val) {
			auto diff = pos - begin();
			if (diff < 0 || static_cast<size_type>(diff) > sz)
				throw std::runtime_error("Iterator out of bounds");
			size_type current{static_cast<size_type>(diff)};
			if (sz >= max_sz)
				reserve(max_sz * 2 +1); // Achtung Sonderfall, wenn keine Mindestgroesze definiert ist
				
			for (Vector::size_type i{sz}; i-- > current;){
				values[i + 1] = values[i];
				}
			values[current] = val;
			++sz;			
			return iterator{values + current};
		}

		iterator erase(const_iterator pos) {
			auto diff = pos - begin();
			if (diff < 0 || static_cast<size_type>(diff) >= sz)
				throw std::runtime_error("Iterator out of bounds");
			size_type current{static_cast<size_type>(diff)};
			for (auto i{current}; i < sz - 1; ++i)
				values[i] = values[i + 1];
			--sz;
			return iterator{values + current};
		}
		
				
   
class IteratorX {
    public:
      using value_type = Vector::value_type;
      using reference = Vector::reference;
      using pointer = Vector::pointer;
      using difference_type = Vector::difference_type;
      using iterator_category = std::forward_iterator_tag;
    private:
      pointer ptr;
      pointer ptr_end;
  		bool firstTheo = true;
      
    public:
      IteratorX() {
      //std::cout << "Xconstr: " << 1<< std::endl;
      ptr = nullptr;
      ptr_end = nullptr;
      }
      
      
      IteratorX(pointer current, pointer end){
      //std::cout << "Xconstr: " << 2 << std::endl;
      //pointer start = current;
      ptr = current;
      ptr_end = end;
      }
      
      IteratorX(const Iterator& it){  // warum hier const? --> you promise not to modify the original Iterator object within the copy constructor. 
      //std::cout << "Xconstr: " << "3 const"<< std::endl;
      ptr = it.get_Ptr();
      ptr_end = it.get_Ptr_end();
  
      }
      
      IteratorX(pointer current){  // Sehr gefährlicher iterator er kennt sein ende nicht
      //std::cout << "Xconstr: " << "3.5 const"<< std::endl;
			ptr = current;
			ptr_end = nullptr;  // ist es nicht gefährlich das er garnicht weiß wo das end ist??
			//std::cout << "null"<< std::endl;
			}
      

      
      bool operator==(ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return true;
        }else{
        	return false;
        }
        
      }
      
      bool operator!=(ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return false;
        }else{
        	return true;
        }
        
      }
      
      
  
      
			iteratorX& operator++() {

        //if (typeid(value_type).name() == typeid(Duchy).name()){
        // std::cout << "hithti"; 
          
        //}
        //auto temp = *ptr;
       	//bool some = temp.get_government() == Government::Theocracy;
        //std::cout << some;
        
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
        	//std::cout << *ptr;
          //std::cout << *(ptr_end -1) ;
        
					if (ptr  - ptr_end == 0 && firstTheo) {
						   return *this;
					} 
					--ptr;
        	std::string vauleType = typeid(value_type).name();
          //if (vauleType == "5Duchy"){
            //auto temp = *ptr;
            //auto some =  temp.get_government();
        //std::cout << some;
          //}
        
					return *this;
			}

			iteratorX operator++(int) {  //postfix
					iteratorX old_itr = *this;
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
					if (ptr + 1 == nullptr) {
						   throw IteratorExtendetOverEndException();
					} 
					++ptr;
					return old_itr;
			}

      
      reference operator*() const  //allows you to access the element at the current iterator position
				{
					//Es ist nicht relevant wo das ende ist es ist nur wichtig, das pointer selber irgendwo hinzeigt
					if (ptr == nullptr ){
						throw DereferencingNullPtrException();
					}

					return *ptr;
				}

				pointer operator->()
				{
					if (ptr == nullptr ){
						throw ArrowToNullPtrException();
					}
				//std::cout << "Not Const arw" << std::endl;
					return ptr;
				}
      
      pointer get_Ptr() const {
      	return ptr;
      }
      pointer get_Ptr_end() const {
      	return ptr_end;
      }
      
      operator const_iterator()  {
        	return ConstIterator(ptr, ptr_end);
      }
      
      
      
  };    
  
  
  
    
class Iterator {
    public:
      using value_type = Vector::value_type;
      using reference = Vector::reference;
      using pointer = Vector::pointer;
      using difference_type = Vector::difference_type;
      using iterator_category = std::forward_iterator_tag;
    private:
      pointer ptr;
      pointer ptr_end;
      
    public:
      Iterator() {
      //std::cout << "constr: " << 1<< std::endl;
      ptr = nullptr;
      ptr_end = nullptr;
      }
      
      
      Iterator(pointer current, pointer end){
      //std::cout << "constr: " << 2 << std::endl;
      ptr = current;
      ptr_end = end;
      }
      
      
      Iterator(pointer current){  
      //std::cout << "constr: " << "3 const"<< std::endl;
			ptr = current;
			ptr_end = nullptr;  
			}
      

      
      bool operator==(ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return true;
        }else{
        	return false;
        }
        
      }
      
      bool operator!=(ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return false;
        }else{
        	return true;
        }
        
      }
      
      
      
      
			iterator& operator++() {				//PreFix
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
					if (ptr_end == nullptr){
						return *this;
					}
					if (ptr  - ptr_end == 0 ) {
						   return *this;
					} 
					++ptr;
					return *this;
			}

			iterator operator++(int) {      //Postfix
					iterator old_itr = *this;
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
					if (ptr_end == nullptr){
						return *this;
					}
					if (ptr  - ptr_end == 0 ) {
						   return *this;
					}  
					++ptr;
					return old_itr;
			}

      
      reference operator*() const  //allows you to access the element at the current iterator position
				{
					if (ptr_end == nullptr){
						throw std::runtime_error("Do not dereference without knowing the end!");
					}
					
				
					if (ptr - ptr_end == 0 ){
						throw DereferencingNullPtrException();
					}
					

					
					return *ptr;
				}

				pointer operator->()
				{
					if (ptr_end == nullptr){
						throw std::runtime_error("Do not point without knowing the end!");
					}
					
					if (ptr - ptr_end == 0 ){
						throw ArrowToNullPtrException();
					}
				//std::cout << "Not Const arw" << std::endl;
					return ptr;
				}
      
      pointer get_Ptr() const {
      	return ptr;
      }
      pointer get_Ptr_end() const {
      	return ptr_end;
      }
      
      operator ConstIterator() const {
        	return ConstIterator(ptr, ptr_end);
      }
      
      
      
  };
  
class ConstIterator {
    public:
      using value_type = Vector::value_type;
      using reference = Vector::reference;
      using pointer = Vector::pointer;
      using difference_type = Vector::difference_type;
      using iterator_category = std::forward_iterator_tag;
    private:
      pointer ptr;
      pointer ptr_end;
      
    public:
      ConstIterator() {
      //std::cout << "Const constr: " << 1<< std::endl;
      ptr = nullptr;
      ptr_end = nullptr;
      }
      
      ConstIterator(pointer current, pointer end){
      //std::cout << "Const constr: " << 2<< std::endl;
      ptr = current;
      ptr_end = end;
  
      }

   		ConstIterator(pointer current){  
      //std::cout << "constr: " << "3.5 const"<< std::endl;
			ptr = current;
			ptr_end = nullptr;  
			}
   

      

      bool operator==(const ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return true;
        }else{
        	return false;
        }
        
      }
      

      
      bool operator!=(const ConstIterator other) const {
        
        if( other.get_Ptr() == ptr){
        	return false;
        }else{
        	return true;
        }
        
      }
      
      
      const ConstIterator& operator++() {
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
					if (ptr_end == nullptr){
						return *this;;
					}
					if (ptr  - ptr_end == 0 ) {
						   return *this;
					}
					++ptr;
					return *this;
			}

			const ConstIterator operator++(int) {
					ConstIterator old_itr = *this;
					if (ptr == nullptr){
							IteratorPointingToZeroException();
					}
					if (ptr_end == nullptr){
						return *this;;
					}
					if (ptr  - ptr_end == 0 ) {
						   return *this;
					}
					++ptr;
					return old_itr;
			}
      
      
      
      reference operator*() const
				{
					if (ptr_end == nullptr){
						throw std::runtime_error("Do not dereference without knowing the end!");
					}
					
				
					if (ptr - ptr_end == 0 ){
						throw DereferencingNullPtrException();
					}
					

					
					return *ptr;
				}
				
				
			pointer operator->() const
				{ 
					if (ptr_end == nullptr){
						throw std::runtime_error("Do not point without knowing the end!");
					}
					
					if (ptr - ptr_end == 0 ){
						throw ArrowToNullPtrException();
					}
				//std::cout << "Not Const arw" << std::endl;
					return ptr;
				}

		
      
      
      
      const pointer get_Ptr() const {
      	return ptr;
      }
      const pointer get_Ptr_end() const {
      	return ptr_end;
      }
      
      friend Vector::difference_type operator-(const const_iterator& lop, const const_iterator& rop) 				{
  				return lop.ptr - rop.ptr;
			}
      
      
      
  };
  
  
  
    
    
    
    

    // Friend function to overload the << operator for Vector objects
    template <typename U>
    friend std::ostream& operator<<(std::ostream& os, const Vector<U>& vec);
    

};

// Overload the << operator to print Vector objects
template <typename T>
std::ostream& operator<<(std::ostream& os, const Vector<T>& vec) {
    os << '[';
    if (vec.sz > 0) {
        os << vec.values[0];
        for (auto i = 1; i < vec.sz; ++i) {
            os << ", " << vec.values[i];
        }
    }
    os << ']';
    return os;
}


#endif
