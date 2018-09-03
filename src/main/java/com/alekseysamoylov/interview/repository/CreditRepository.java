package com.alekseysamoylov.interview.repository;


import com.alekseysamoylov.interview.CreditService.CreditInformation;
import java.util.Collections;
import java.util.List;

public interface CreditRepository {

  default List<CreditInformation> getAllCreditInformation() {return Collections.emptyList();}

  default void save(CreditInformation creditInformation) {}

}
