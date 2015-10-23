package nl.marcusink.mmo.server.database.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avatars", schema = "public")
public class Avatar {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "profession")
    private String profession;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "gender", nullable = false)
    private String gender;

    // LEVELS IN EXP VALUES
    @Column(name = "attack")
    private double attack;

    @Column(name = "strength")
    private double strength;

    @Column(name = "defence")
    private double defence;

    @Column(name = "range")
    private double range;

    @Column(name = "prayer")
    private double prayer;

    @Column(name = "magic")
    private double magic;

    @Column(name = "consitution")
    private double constitution;

    @Column(name = "crafting")
    private double crafting;

    @Column(name = "mining")
    private double mining;

    @Column(name = "smithing")
    private double smithing;

    @Column(name = "fishing")
    private double fishing;

    @Column(name = "cooking")
    private double cooking;

    @Column(name = "firemaking")
    private double firemaking;

    @Column(name = "woodcutting")
    private double woodcutting;

    @Column(name = "herblore")
    private double herblore;

    @Column(name = "thieving")
    private double thieving;

    @Column(name = "fletching")
    private double fletching;

    @Column(name = "slayer")
    private double slayer;

    @Column(name = "farming")
    private double farming;

    @Column(name = "hunting")
    private double hunting;

    public Avatar() {
        this.attack = 0;
        this.strength = 0;
        this.defence = 0;
        this.range = 0;
        this.prayer = 0;
        this.magic = 0;
        this.constitution = 0;
        this.crafting = 0;
        this.mining = 0;
        this.smithing = 0;
        this.fishing = 0;
        this.cooking = 0;
        this.firemaking = 0;
        this.woodcutting = 0;
        this.herblore = 0;
        this.thieving = 0;
        this.fletching = 0;
        this.slayer = 0;
        this.farming = 0;
        this.hunting = 0;
    }

    public String getName() {
        return name;
    }

    public void setCharacterName(String characterName) {
        this.name = characterName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getPrayer() {
        return prayer;
    }

    public void setPrayer(double prayer) {
        this.prayer = prayer;
    }

    public double getMagic() {
        return magic;
    }

    public void setMagic(double magic) {
        this.magic = magic;
    }

    public double getConstitution() {
        return constitution;
    }

    public void setConstitution(double constitution) {
        this.constitution = constitution;
    }

    public double getCrafting() {
        return crafting;
    }

    public void setCrafting(double crafting) {
        this.crafting = crafting;
    }

    public double getMining() {
        return mining;
    }

    public void setMining(double mining) {
        this.mining = mining;
    }

    public double getSmithing() {
        return smithing;
    }

    public void setSmithing(double smithing) {
        this.smithing = smithing;
    }

    public double getFishing() {
        return fishing;
    }

    public void setFishing(double fishing) {
        this.fishing = fishing;
    }

    public double getCooking() {
        return cooking;
    }

    public void setCooking(double cooking) {
        this.cooking = cooking;
    }

    public double getFiremaking() {
        return firemaking;
    }

    public void setFiremaking(double firemaking) {
        this.firemaking = firemaking;
    }

    public double getWoodcutting() {
        return woodcutting;
    }

    public void setWoodcutting(double woodcutting) {
        this.woodcutting = woodcutting;
    }

    public double getHerblore() {
        return herblore;
    }

    public void setHerblore(double herblore) {
        this.herblore = herblore;
    }

    public double getThieving() {
        return thieving;
    }

    public void setThieving(double thieving) {
        this.thieving = thieving;
    }

    public double getFletching() {
        return fletching;
    }

    public void setFletching(double fletching) {
        this.fletching = fletching;
    }

    public double getSlayer() {
        return slayer;
    }

    public void setSlayer(double slayer) {
        this.slayer = slayer;
    }

    public double getFarming() {
        return farming;
    }

    public void setFarming(double farming) {
        this.farming = farming;
    }

    public double getHunting() {
        return hunting;
    }

    public void setHunting(double hunting) {
        this.hunting = hunting;
    }

    public int getSkillLevel(double exp) {
        double x = 0;
        double growth = 1.1040895136738123376495053876233;
        for (int i = 1; i < 99; i++) {
            x = Math.round(x + (75 * Math.pow(growth, i)));

            if (exp < x) {
                return i;
            }
        }
        return 99;
    }
}
