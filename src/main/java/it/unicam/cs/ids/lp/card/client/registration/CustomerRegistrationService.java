package it.unicam.cs.ids.lp.card.client.registration;

import it.unicam.cs.ids.lp.card.client.Customer;
import it.unicam.cs.ids.lp.card.client.CustomerAccount;
import it.unicam.cs.ids.lp.card.client.CustomerAccountRepository;
import it.unicam.cs.ids.lp.card.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService
        implements CustomerRegistry<Customer, CustomerAccount> {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public boolean registerCustomer(Customer customer, CustomerAccount customerAccount) {
        if (!registrationValuesCorrectness(customer))
            return false;
        customerRepository.save(customer);
        customerAccountRepository.save(customerAccount);
        return true;
    }

    public boolean registrationValuesCorrectness(Customer customer) {
        return customer == null
                || isNameValid(customer.getName())
                && isSurnameValid(customer.getSurname())
                && isTelephoneNumberValid(customer.getTelephoneNumber())
                && isEmailValid(customer.getEmail());
    }

    /**
     * Verifica che il nome sia valido
     *
     * @param name il nome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isNameValid(String name) {
        return name == null
                || name.length() > 0
                && name.length() < 255
                && !name.contains("\\.[]{}()<>*+-=!?^$|");
    }

    /**
     * Verifica che il cognome sia valido
     *
     * @param surname cognome da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isSurnameValid(String surname) {
        return isNameValid(surname);
    }

    /**
     * Verifica che il numero di telefono sia valido
     *
     * @param telephoneNumber il numero da verificare
     * @return true se è scritto correttamente, false altrimenti
     */
    protected boolean isTelephoneNumberValid(String telephoneNumber) {
        return telephoneNumber == null
                || telephoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    }

    /**
     * Verifica che l'email sia valida
     *
     * @param email l'email
     * @return true se l'email è valida, false altrimenti
     */
    protected boolean isEmailValid(String email) {
        return email == null
                || email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public void unregisterCustomerByName(String name) {
        customerRepository.deleteById(name);
    }
}
