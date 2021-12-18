package repository.file;

import repository.memory.FriendshipRepository;
import domain.Friendship;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FriendshipFileRepo extends FriendshipRepository {
    public String fileName;

    public FriendshipFileRepo(String fileName) {
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
                Friendship friendship = new Friendship(Integer.parseInt(attributes.get(0)), Integer.parseInt((attributes.get(1))), LocalDateTime.parse(attributes.get(2)));
                super.add(friendship);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(Friendship friendship)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(createFriendshipAsString(friendship));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData()
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Friendship f:friendships)
            {
                bw.write(createFriendshipAsString(f));
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createFriendshipAsString(Friendship friendship)
    {
        return friendship.getId1() + ";" + friendship.getId2() + ";";
    }

    @Override
    public void add(Friendship friendship)
    {
        super.add(friendship);
        writeToFile(friendship);
    }

    @Override
    public Friendship delete(Friendship friendship)
    {
        Friendship friendshipDeleted =super.delete(friendship);
        saveData();
        return friendshipDeleted;
    }
}
