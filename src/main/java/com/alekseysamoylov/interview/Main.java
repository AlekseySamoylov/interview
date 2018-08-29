package com.alekseysamoylov.interview;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class created by Aleksey Samoylov
 */
public class Main {
  static Logger logger = LoggerFactory.getLogger("Main");
  static int interrupt = 0;

  public static void main(String[] args) throws InterruptedException {
    Map<UserData, CreditInformation> ucm = new HashMap<>(100);

    UserData u = new UserData(10l, "Name");
    CreditInformation c = new CreditInformation(1000000l, "c", 10);

    ucm.put(u, c);

    Long creditInformationId = 1000000l;
    Long userDataId = 10l;
    CreditInformation ci = ucm.get(u);
    if (ci.getId() == creditInformationId) {
      logger.info("Credit information: " + ci.getAmountOfMoney() + " " + u.name);
    } else if (u.id == userDataId) {
      System.out.println("Equality user id");
    }

    new Thread(() -> {
      while (interrupt == 0) {
        try {
          // do something
          Thread.sleep(1000);
        } catch (InterruptedException e) {}
      }
    }).start();

    interrupt = 1;

    CreditInformation ci2 = ucm.get(new UserData(10l, "Name"));

    try {
      System.err.println("Error: " + ci2.getAmountOfMoney());
    } catch (Exception ex) {
    }
    System.err.println("Error: " + ci2.getName());
  }


  private static class UserData {
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

  private static class CreditInformation implements Serializable {

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
     * @return id
     */
    public Long getId() {
      return id;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * get amountOfMoney
     * @return amountOfMoney
     */
    public double getAmountOfMoney() {
      return amountOfMoney;
    }
  }
}
