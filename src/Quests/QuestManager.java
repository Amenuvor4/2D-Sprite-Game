package Quests;

import main.GamePanel;

import java.util.ArrayList;

public class QuestManager {
    public ArrayList<Quest> activeQuests = new ArrayList<>();
    private GamePanel gp;

    public QuestManager(GamePanel gp) {
        this.gp = gp;
    }

    // Add a quest to the active quests list
    public void addQuest(Quest quest) {
        activeQuests.add(quest);
    }

    // Update all active quests
    public void updateQuests() {
        for (Quest quest : activeQuests) {
            quest.update();
        }
    }

    // Create or retrieve a quest by name
    public Quest createQuest(String questName) {
        switch (questName) {
            case "Quest1":
                return new Quest1(gp);
            // Add more cases for other quests
            default:
                System.out.println("Unknown quest name: " + questName);
                return null;
        }
    }
}
