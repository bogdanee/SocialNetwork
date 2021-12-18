package repository.file;

import repository.memory.UserRepository;
import domain.User;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UserFileRepository extends UserRepository {
    String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData()
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while((line = br.readLine())!=null){
                List<String> attributes = Arrays.asList(line.split(";"));
                User user = new User(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3));
                super.add(user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(User user)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(createUserAsString(user));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData()
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (User u: users)
            {
                bw.write(createUserAsString(u));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createUserAsString(User user)
    {
        return user.getId() + ";" + user.getFirstName() + ";" + user.getLastName() + ";";
    }

    @Override
    public void add(User user) throws SQLException {
        super.add(user);
        writeToFile(user);
    }

    @Override
    public User delete(User user)
    {
        User userDeleted = super.delete(user);
        saveData();
        return userDeleted;
    }

    @Override
    public void update(User user)
    {
        super.update(user);
        saveData();
    }
}
