package de.kasoki.jfeedly;

import de.kasoki.jfeedly.components.OnAuthenticatedListener;
import de.kasoki.jfeedly.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Example {

    private static OnAuthenticatedListener listener = new OnAuthenticatedListener() {
        @Override
        public void onAuthenticated(JFeedly feedly) {
            System.out.println("JFeedly is now Authenticated");

            // get profile
            Profile profile = feedly.getProfile();

            System.out.println("Hello, " + profile.getGivenName() + " " + profile.getFamilyName());

            // get categories
            Categories categories = feedly.getCategories();

            System.out.println("\nThese are your categories:");

            for(Category c : categories) {
                System.out.println("* " + c.getLabel());
            }

            // get subscriptions
            Subscriptions subscriptions = feedly.getSubscriptions();

            System.out.println("\nThese are your subscriptions:");

            for(Subscription s : subscriptions) {
                System.out.println("* " + s.getTitle() + " - Categories: " + s.getCategoryIds().size());
            }

            ArrayList<Category> cList = new ArrayList<Category>();

            cList.add(categories.get(0));
            cList.add(categories.get(1));
            cList.add(categories.get(2));

            // get first subscription
            Subscription s = subscriptions.get(0);

            // add subscription
            //feedly.subscribe("http://kasoki.de/rss", "Kasokis Blog", cList);

            // update subscription
            //s.setTitle("OH MAI GOSH");
            //s.update(feedly);

            // get tags
            Tags tags = feedly.getTags();

            System.out.println("\nTags:");

            for(Tag t : tags) {
                System.out.println("* " + t.getId() + (t.getLabel() != null ? ", Label: " + t.getLabel() : ""));
            }

            // search
            System.out.println(feedly.searchFeeds("kasoki"));

            // get all entries

            System.out.println("\nEntries:");

            Entries entries = feedly.getEntries(20);

            for(Entry entry : entries) {
                System.out.println("* " + entry.getTitle() + " by " + entry.getAuthor());
            }
        }
    };

    public static void main(String[] args) {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String secretApiKey = prop.getProperty("secret_api_key");

        JFeedly feedly = JFeedly.createSandboxHandler(secretApiKey);

        feedly.setVerbose(true);

        feedly.setOnAuthenticatedListener(listener);

        feedly.authenticate();
    }
}
