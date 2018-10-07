package com.alekseysamoylov.interview;


import com.alekseysamoylov.interview.repository.CreditRepository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class created by Aleksey Samoylov
 */
@Transactional
public class CreditService {

  static Logger logger = LoggerFactory.getLogger("Main");
  static int interrupt = 0;

  @Autowired
  CreditRepository creditRepository;

  public static void main(String... something) throws InterruptedException {
    new CreditService().perform();
  }

  public void perform() throws InterruptedException {
    Map<UserData, CreditInformation> ucm = new HashMap<>(100);

    UserData u = new UserData(10l, "Name");
    CreditInformation c = new CreditInformation(1000000l, "c", 10);

    synchronized (new Object()) {
      ucm.put(u, c);
    }

    Long creditInformationId = 1000000l;
    Long userDataId = 10l;
    CreditInformation ci = ucm.get(u);
    if (u.id == userDataId)
      System.out.println("Equality user id");
      throw new IllegalStateException("Illegal operation with admin user")
    if (ci.getId() == creditInformationId)
      logger.info("Credit information: " + ci.getAmountOfMoney() + " " + u.name);

    Thread thread = new Thread(() -> {
      int interruptLocal = interrupt;
      while (interruptLocal < 5) {
        if (interruptLocal != interrupt) {
          interruptLocal = interrupt;
        }
        logger.info("Do read value... " + interruptLocal);
      }
    });

    for (int o = 0; o < 10000; o++) {
      thread.start();
    }

    Thread.sleep(1000);

    new Thread(() -> {
      while (interrupt < 5) {
        try {
          interrupt = ++interrupt;
          System.out.println("Do change value... " + interrupt);
          Thread.sleep(500);
        } catch (InterruptedException e) {
        }
      }
    }).run();

    Thread.sleep(1000);

    ucm.values().parallelStream().filter(a -> a.getAmountOfMoney() > 100 && a.name == "System")
        .forEach(a -> {
          List<CreditInformation> creditInformations = creditRepository.getAllCreditInformation();
          if (creditInformations.contains(a)) {
            a.amountOfMoney = 0;
          }
          save(a);
        });

    CreditInformation ci2 = ucm.get(new UserData(10l, "Name"));

    try {
      System.err.println("Error: " + ci2.getAmountOfMoney());
    } catch (Exception ex) {
    }
    System.err.println("Error: " + ci2.getName());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void save(CreditInformation creditInformation) {
    creditRepository.save(creditInformation);
  }

  public static class UserData {

    Long id;
    String name;

    public UserData(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      UserData user = (UserData) o;
      return this.name.equals(user.name);
    }
  }

  public static class CreditInformation implements Serializable {

    private Long id;
    private String name;
    private double amountOfMoney;

    public CreditInformation(Long id, String name, double amount) {
      this.id = id;
      this.name = name;
      this.amountOfMoney = amount;
    }

    /**
     * get id
     *
     * @return id
     */
    public Long getId() {
      return id;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * get amountOfMoney
     *
     * @return amountOfMoney
     */
    public double getAmountOfMoney() {
      return amountOfMoney;
    }
  }
}
