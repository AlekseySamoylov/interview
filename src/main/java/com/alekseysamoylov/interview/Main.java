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

  public static void main(String[] args) {
    Map<UserData, CreditInformation> ucm = new HashMap<>(24);

    UserData u1 = new UserData(1345l, "Name1");
    CreditInformation c1 = new CreditInformation(30000l, "c1", 4);

    UserData u2 = new UserData(2000000l, "Name2");
    CreditInformation c2 = new CreditInformation(30000l, "c1", 4);


    ucm.put(u1, c1);
    ucm.put(u2, c2);

    Long id = 2000000l;
    CreditInformation ci = ucm.get(u2);
    if (ci.getId() == id) {
      logger.info("Credit information: " + ci.getAmount() + " " + u2.name);
    }

    CreditInformation ci2 = ucm.get(new UserData(2000000l, "Name2"));

    System.err.println("Error: " + ci2.getAmount());

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
    private double amount;

    public CreditInformation(Long id, String name, double amount) {
      this.id = id;
      this.name = name;
      this.amount = amount;
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
     * get amount
     * @return amount
     */
    public double getAmount() {
      return amount;
    }
  }
}
