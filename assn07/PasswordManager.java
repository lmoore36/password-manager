package assn07;

import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.util.*;

public class PasswordManager<K,V> implements Map<K,V> {
    public static final String MASTER_PASSWORD = "password321";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }

    private int doHash (K key) {
       int hashIndex = key.hashCode();
       hashIndex = Math.abs(hashIndex);

       return hashIndex % _passwords.length;
    }

    // TODO: put
    @Override
    public void put(K key, V value) {
        int index = doHash(key);

        //if you hash the website and the table is empty, update with a new account
        if(_passwords[index] == null) {
            _passwords[index] = new Account<>(key, value);
            return;
        }

        //initialize my linked list
        Account<K, V> currentPointer = _passwords[index];

        while (true) {
            // if key does not match current node, walk the list
            if (currentPointer.getWebsite() != key) {

                // if you get to the end of the list without the keys matching
                if (currentPointer.getNext() == null) {
                    Account<K, V> node = new Account<>(key, value);
                    currentPointer.setNext(node);
                    return;
                }

                else {
                    currentPointer = currentPointer.getNext();
                }
            }

            // if key matches the current node, update the value and exit
            if (currentPointer.getWebsite() == key) {
                currentPointer.setPassword(value);
                return;
            }
        }
    }

    // TODO: get
    @Override
    public V get(K key) {
        int index = doHash(key);

        //if you hash the website and the table is empty, return null
        if(_passwords[index] == null) {
            return null;
        }

        //initialize my linked list
        Account<K,V> currentPointer = _passwords[index];

        while (true) {
            // if key does not match current node, walk the list
            if (!currentPointer.getWebsite().equals(key)) {
                // if you get to the end of the list without the keys matching
                if (currentPointer.getNext() == null) {
                    return null;
                } else {
                    currentPointer = currentPointer.getNext();
                }
            }

            // if key matches the current node, update the value and exit
            if (currentPointer.getWebsite().equals(key)) {
                return currentPointer.getPassword();
            }
        }
    }

    // TODO: size
    @Override
    public int size() {
        int elements = 0;

        for(int i = 0; i < _passwords.length; i++) {
            Account<K, V> currentPointer = _passwords[i];

            while (currentPointer != null) {
                elements += 1;
                currentPointer = currentPointer.getNext();
            }
        }
        return elements;
    }

    // TODO: keySet
     @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<K>();

        for(int i = 0; i < _passwords.length; i++) {
            Account<K, V> currentPointer = _passwords[i];

            while (currentPointer != null) {
                keys.add(currentPointer.getWebsite());
                currentPointer = currentPointer.getNext();
            }
        }
        return keys;
    }

    // TODO: remove
    @Override
    public V remove(K key) {
        int index = doHash(key);

        if(_passwords[index] == null) {
            return null;
        }

        Account<K,V> previousPointer = null;
        Account<K, V> currentPointer = _passwords[index];

        while (true) {

            // does the current node match the key
            if (currentPointer.getWebsite().equals(key)) {

                // goal: remove the current node, return the value (password)
                // step 1 - remove the current node

                if (previousPointer == null) {
                    // currentPointer at head of list, make next node new head (removing current node)
                    _passwords[index] = currentPointer.getNext();
                } else {
                    //
                    previousPointer.setNext(currentPointer.getNext());
                }

                // step 2 - return the password from the removed node
                return currentPointer.getPassword();
            }

            else {

                // goal: advance the current pointer, or exit

                // check to see if we are at the end of the list

                if (currentPointer.getNext() == null) {
                    return null;
                } else {
                    // advance the pointers
                    previousPointer = currentPointer;
                    currentPointer = currentPointer.getNext();
                }
            }
        }
    }

    // TODO: checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
        ArrayList<K> matchingPassword = new ArrayList<K>();

        for(int i = 0; i < _passwords.length; i++) {
            Account<K, V> currentPointer = _passwords[i];

            while (currentPointer != null) {
                if (currentPointer.getPassword().equals(value)) {
                    matchingPassword.add(currentPointer.getWebsite());
                }
                currentPointer = currentPointer.getNext();
            }
        }
        return matchingPassword;
    }

    // TODO: checkMasterPassword
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
        if(enteredPassword.compareTo(MASTER_PASSWORD) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Generates random password of input length
     */
    @Override
    public String generateRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /*
    Used for testing, do not change
     */
    public Account[] getPasswords() {
        return _passwords;
    }
}
