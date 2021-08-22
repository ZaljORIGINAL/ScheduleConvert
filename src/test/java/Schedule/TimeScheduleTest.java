package Schedule;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class TimeScheduleTest {

    @Test
    public void TimeScheduleConstructorTest() throws URISyntaxException, IOException {
        Path times = Path.of((TimeScheduleTest.class.getResource("/times.txt")).toURI());
        TimeSchedule schedule = new TimeSchedule(times);
        assertEquals(4, schedule.getTimes().size());
    }

    @Test
    public void getPositionByTimeTest() throws IOException, URISyntaxException {
        Path times = Path.of((TimeScheduleTest.class.getResource("/times.txt")).toURI());
        TimeSchedule schedule = new TimeSchedule(times);
        assertEquals(4, schedule.getTimes().size());
        schedule.getPositionByTime("9:40");
    }
}