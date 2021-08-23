package Schedule;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TimeSchedule {
    private final List<TimeSession> times;

    public TimeSchedule(Path timeFile) throws IOException {
        times = new ArrayList<>();
        readSchedule(timeFile);
    }

    private void readSchedule(Path timeFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(timeFile.toFile()))) {
            String line = reader.readLine();
            if (line != null){
                String[] startFinish = line.split(" ");
                TimeSession lastReaded = new TimeSession(
                        Integer.parseInt(startFinish[0]),
                        Integer.parseInt(startFinish[1]));
                times.add(lastReaded);

                line = reader.readLine();
                while (line != null) {
                    startFinish = line.split(" ");
                    TimeSession readed = new TimeSession(
                            Integer.parseInt(startFinish[0]),
                            Integer.parseInt(startFinish[1]));

                    if (lastReaded.getFinish() < readed.getStart()) {
                        times.add(readed);
                        lastReaded = readed;
                    }
                    else
                        throw new RuntimeException("Ошибка в раписании времени");

                    line = reader.readLine();
                }
            }
            else
                throw new RuntimeException("Ошибка в раписании времени");
        }
    }

    public List<TimeSession> getTimes() {
        return times;
    }

    public int getPositionByTime (String timeStr) {
        int timeSipIndex = timeStr.indexOf(":");
        int time;
        if (timeSipIndex != -1)
             time = Integer.parseInt(
                timeStr.substring(0, timeSipIndex) +
                        timeStr.substring(timeSipIndex + 1));
        else
            time = Integer.parseInt(timeStr);

        for (TimeSession session : times) {
            if (time == session.getStart())
                return times.indexOf(session);
        }
        return -1;
    }
}
