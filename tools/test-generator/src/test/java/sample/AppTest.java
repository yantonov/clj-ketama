package sample;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class AppTest

{
    @Test
    public void simple() throws NoSuchAlgorithmException {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("abc", 2));
        servers.add(new Server("def", 10));
        servers.add(new Server("xyz", 19));
        printRingDistribution(servers);
    }

    private void printRingDistribution(List<Server> servers) throws NoSuchAlgorithmException {
        int totalWeight = 0;
        for (int i = 0; i < servers.size(); ++i) {
            totalWeight += servers.get(i).getWeight();
        }
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        TreeMap<Long, Server> buckets = new TreeMap<>();

        for (int i = 0; i < servers.size(); i++) {

            int thisWeight = servers.get(i).getWeight();

            double factor = Math.floor(((double) (40 * servers.size() * thisWeight)) / (double) totalWeight);

            for (long j = 0; j < factor; j++) {
                byte[] d = md5.digest((servers.get(i).getName() + "-" + j).getBytes());
                for (int h = 0; h < 4; h++) {
                    Long k =
                            ((long) (d[3 + h * 4] & 0xFF) << 24)
                                    | ((long) (d[2 + h * 4] & 0xFF) << 16)
                                    | ((long) (d[1 + h * 4] & 0xFF) << 8)
                                    | ((long) (d[0 + h * 4] & 0xFF));
                    buckets.put(k, servers.get(i));
                }
            }
        }

        List<Point> points = buckets.entrySet()
                .stream()
                .map(entry -> new Point(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        Collections.sort(points, (o1, o2) -> {
            if (o1.getHash() < o2.getHash())
                return -1;
            if (o1.getHash() > o2.getHash())
                return 1;
            return 0;
        });

        for (Point point : points) {
            System.out.println(String.format("%d \"%s\"", point.getHash(), point.getServer().getName()));
        }
    }
}

class Point {
    private final long hash;
    private final Server server;

    public Point(long hash, Server server) {
        this.hash = hash;
        this.server = server;
    }

    public long getHash() {
        return hash;
    }

    public Server getServer() {
        return server;
    }
}

class Server {
    private final String name;
    private final int weight;

    public Server(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}


