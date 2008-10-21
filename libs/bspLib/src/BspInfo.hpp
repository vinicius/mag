#ifndef BspInfo_HPP
#define BspInfo_HPP

#include <string>
#include <iostream>

class BspInfo{

private:
  
  bool isProcessZero_;
  std::string processZeroIor_;
  
public:
  
  BspInfo(){}
  
  BspInfo(bool isProcessZero,
	  const std::string & processZeroIor):isProcessZero_(isProcessZero),
                                              processZeroIor_(processZeroIor){}
  
  bool isProcessZero() const{ return isProcessZero_; }
  const std::string & processZeroIor() const{ return processZeroIor_; }
  
  void isProcessZero(bool isProcessZero){ isProcessZero_ = isProcessZero; }
  void processZeroIor(const std::string & processZeroIor){ processZeroIor_ = processZeroIor; }
  
  void dump() const {
    std::cout << "Dumping BspInfo: " << std::endl
	      << "isProcessZero: "   << (isProcessZero_?"true":"false") << std::endl
	      << "processZeroIor: "  << processZeroIor_ << std::endl;
  }
  
  
  
};

#endif//BspInfo_HPP


