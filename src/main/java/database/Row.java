package database;

public class Row {
    private final int id;
    private final short dayOfWeek;
    private final short time;
    private final short weekType;
    private final String disciplineName;
    private final String building;
    private final String classRoom;
    private final short disciplineType;
    private final String educator;

    public Row(int id, short dayOfWeek, short time, short weekType, String disciplineName, String building, String classRoom, short disciplineType, String educator) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.weekType = weekType;
        this.disciplineName = disciplineName;
        this.building = building;
        this.classRoom = classRoom;
        this.disciplineType = disciplineType;
        this.educator = educator;
    }

    public Row(int id, Row row) {
        this.id = id;
        this.dayOfWeek = row.getDayOfWeek();
        this.time = row.getTime();
        this.weekType = row.getWeekType();
        this.disciplineName = row.getDisciplineName();
        this.building = row.getBuilding();
        this.classRoom = row.getClassRoom();
        this.disciplineType = row.getDisciplineType();
        this.educator = row.getEducator();
    }

    public Row(short dayOfWeek, short time, short weekType, String disciplineName, String building, String classRoom, short disciplineType, String educator) {
        this.id = -1;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.weekType = weekType;
        this.disciplineName = disciplineName;
        this.building = building;
        this.classRoom = classRoom;
        this.disciplineType = disciplineType;
        this.educator = educator;
    }

    public int getId() {
        return id;
    }

    public short getDayOfWeek() {
        return dayOfWeek;
    }

    public short getTime() {
        return time;
    }

    public short getWeekType() {
        return weekType;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public String getBuilding() {
        return building;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public short getDisciplineType() {
        return disciplineType;
    }

    public String getEducator() {
        return educator;
    }
}
