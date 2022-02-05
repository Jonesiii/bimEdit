/*
 * Laki2 harjoitustyö
 * Joonas Arola, joonas.arola@tuni.fi
 */
 
import java.util.Scanner;

import java.io.*;
 
public class BimEdit {

    // esitellään komennot luokkavakioina
    public static final String TULOSTA = "print";
    public static final String INFO = "info";
    public static final String VAIHDA = "invert";
    public static final String LAAJENNA = "dilate";
    public static final String PIENENNA = "erode";
    public static final String LATAA = "load";
    public static final String LOPETA = "quit";
        
        
    // metodi vaihtaa saamassaan kaksiulotteisessa taulukossa olevat toisena ja kolmantena 
    // parametrina saamansa merkit päikseen ja palauttaa totuusarvon true, mikäli paramet-
    // rina saadulle taulukolle on varattu muistia ja taulukon koko on vähintään 1 x 1 -alkiota.
    public static boolean vaihdaMerkit (char[][] kuva, char[] merkit) {
        if (kuva != null && kuva.length > 0 && kuva[0].length > 0) {
            for (int rivi = 0; rivi < kuva.length; rivi++) {
                for (int sarake = 0; sarake < kuva[rivi].length; sarake++) {
                    if (kuva[rivi][sarake] == merkit[0]) {
                        kuva[rivi][sarake] = merkit[1];
                    }
                    else if (kuva[rivi][sarake] == merkit[1]) {
                        kuva[rivi][sarake] = merkit[0];
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    // metodi vaihtaa päikseen yksiuloitteisen taulukon merkit, joka ei saa olla
    // kooltaan kuin 2 alkion mittainen. 
    public static char[] vaihdaMerkit (char[] merkit) {
        if (merkit != null && merkit.length == 2) {
            char a = merkit[0];
            char b = merkit[1];
            
            merkit[0] = b;
            merkit[1] = a;
        }
        return merkit;
    }
    
    
    // metodi tulostaa saamansa kaksiulotteisen taulukon sisällön näytölle
    // ilman erottimia ja yksi rivi kerrallaan
    public static void tulosta2d (char[][] kuva) {
        if (kuva != null) {
            for (int rivi = 0; rivi < kuva.length; rivi++) {
                for (int sarake = 0; sarake < kuva[rivi].length; sarake++) {
                    System.out.print(kuva[rivi][sarake]);
                }
            //rivinvaihto
            System.out.println();    
            }
        }
    }
    
    
    
    // metodi kopio saamansa kaksiulotteisen taulukon ja palauttaa kopion
    public static char[][] kopioi2dtaulukko (char[][] kuva) {
    if (kuva == null || kuva.length < 1 || kuva[0].length < 1) {
            return null;
        }
        else {
            char[][] kuva2 = new char[kuva.length][kuva[0].length];
            for (int i = 0; i < kuva.length; i++) {
                for (int j = 0; j < kuva[i].length; j++) {
                    kuva2[i][j] = kuva[i][j];
                }
            }
            return kuva2;
        }
    }
    
    
    
    /*
    * Laskee montako esiintymää kullakin yksiulotteisessa taulukossa olevalla
    * merkillä on kaksiulotteisessa merkkien taulukossa. Metodi luo ja palauttaa
    * yksiulotteisen taulukon, joka sisältää laskettavien merkkien lukumäärät
    * samassa järjestyksessä kuin merkit ovat parametrina saadussa yksiulotteisessa
    * merkkien taulukossa. Paluuarvo on null, jos jommallekummalle taulukolle
    * tai molemmille taulukoille ei ole varattu muistia tai jos jompikumpi
    * tai kumpikin taulukko on tyhjä.
    *
    */
    public static int[] laskeFrekvenssit(char[] merkit, char[][] kuva) {
        // Viite esiintymien lukumäärät sisältävään taulukkoon.
        int[] lukumaarat = null; 
      
        // taulukkoja voidaan käsitellä.
        if (merkit != null && kuva != null && merkit.length > 0
        && kuva.length > 0 && kuva[0].length > 0) {
            // Luodaan taulukko-olio ja liitetään viite olioon. taulukon pituus on
            // sama kuin laskettavien merkkien lukumäärä.
            lukumaarat = new int[merkit.length];
      
            // Käydään läpi kaikki taulukon alkiot kaksinkertaisen silmukan avulla.
            for (int rivind = 0; rivind < kuva.length; rivind++) {
                for (int sarind = 0; sarind < kuva[rivind].length; sarind++) {
                    // Haetaan alkion arvoa laskettavista merkeistä.
                    char merkki = kuva[rivind][sarind];
                    for (int ind = 0; ind < merkit.length; ind++) {
                        // Löydettiin laskettava merkki.
                        if (merkki == merkit[ind]) {
                            // Kasvatetaan merkin lukumäärää yhdellä.
                            lukumaarat[ind]++;
                        }
                    }
                }
            }         
        }
        // Palautetaan viite taulukko-olioon tai null-arvo.
        return lukumaarat;
    }

    /*
     * Metodi lataa tiedoston merkit annetun nimisestä kuvatiedostosta taulukkoon ja
     * sijoittaa taustavärin merkin kahden alkion kokoisen merkit-taulukon ensimmäiseen
     * alkioon ja edustavärin toiseen alkioon. Paluuarvo on viite metodin luomaan
     * taulukkoon, jossa kuvan merkit ovat. Paluuarvo on null-arvoinen, jos taulukko-
     * parametri on null-arvoinen tai siinä on väärä määrä merkkejä tai tiedostoa
     * lukiessa tapahtuu virhe.
     */
    public static char[][] lataaKuvataulukkoon(String tiedostonNimi, char[] merkit) {
        // Viite kuvan merkit sisältävään taulukkoon.
        char[][] kuva = null;

        // Esitellään viite try-lohkon ulkopuolella, jotta voidaan sulkea lukija
        // tarvittaessa virheen tapahtuessa.
        Scanner tiedostonLukija = null;

        // Varaudutaan poikeukseen.
        try {
            // Avataan tiedosto.
            File tiedosto = new File(tiedostonNimi);

            // Liitetään lukija tiedostoon.
            tiedostonLukija = new Scanner(tiedosto);

            // Luetaan kuvan rivien ja sarakkeiden lukumäärät.
            int rivlkm = Integer.parseInt(tiedostonLukija.nextLine());
            int sarlkm = Integer.parseInt(tiedostonLukija.nextLine());

            // Jatketaan, jos koot ovat järkeviä, eikä tiedostossa ole virheitä.
            if (rivlkm >= 3 && sarlkm >= 3 && merkit.length == 2) {
             
                // Esitellään viite, luodaan taulukko ja liitetään viite taulukkoon.
                kuva = new char[rivlkm][sarlkm];

                // Luetaan kuvan taustaa ja edustaa symboloivat merkit.
                merkit[0] = tiedostonLukija.nextLine().charAt(0);
                merkit[1] = tiedostonLukija.nextLine().charAt(0);

                // Laskuri rivi-indeksille.
                int rivind = 0;

                // Luetaan kuvan merkkejä riveittäin ja sijoitetaan merkit taulukon
                // riveille.
                while (tiedostonLukija.hasNextLine()) {
                    // Luetaan rivi tiedostosta.
                    String rivi = tiedostonLukija.nextLine();
                    
                    // katkaistaan metodin eteneminen, jos rivi osoittautuu erimittaiseksi kuin
                    // kuuluisi
                    if (rivi.length() != sarlkm) {
                        kuva = null;
                        break;
                    }
                    else {
                        // Sijoitetaan rivin merkit taulukon riville.
                        int sarind = 0;
                        while (sarind < sarlkm) {
                            // varmistetaan, että tiedostossa ei ole vääriä merkkejä ja
                            //  palautetaan null mikäli vääriä merkkejä löytyy
                            if (rivi.charAt(sarind) == merkit[0] || rivi.charAt(sarind) == merkit[1]) {
                                kuva[rivind][sarind] = rivi.charAt(sarind);
                                sarind++;
                            }
                            else {
                                kuva = null;
                                break;
                            }
                        }
                    }

                    // Siirrytään seuraavalle riville.
                    rivind++;
                }
                // tarkastetaan vielä, että rivejä on oikea määrä
                if (rivind != rivlkm) {
                    kuva = null;
                }
                
                // Suljetaan lukija ja virta.
                tiedostonLukija.close();
            }
        }

      // Siepataan mikä tahansa poikkeus. Tänne päädytään myös, jos jälkimmäinen
      // parametri on null-arvoinen.
      catch (Exception e) {
         kuva = null;
         // Suljetaan lukija vain, jos sellainen ehdittiin luoda.
         if (tiedostonLukija != null) {
            tiedostonLukija.close();
         }
      }

      // Palautetaan viite taulukkoon tai virheestä kertova arvo.
      return kuva;
    }
   
   
   
    // metodi laajentaa saamaansa kuvaa hyödyntäen toisena parametrina saamaansa
    // lukua n(koko) niin, että n x n -kokoisen laatikon avulla päätellään kuuluuko kuvaan
    // lisätä edustamerkki vai ei 
    public static char[][] laajennaKuvaa (char[][] kuva, char[] merkit, int koko) {
        
        // päätellään n x n -kuvion keskipisteen ja reunan erotus 
        int erotus = (int) koko / 2;
        
        
        // kopioidaan ensin kuva, jotta alkuperäistä voidaan käyttää referenssinä
        // koko iteraation ajan
        char[][] kuvaKopio = kopioi2dtaulukko(kuva);

        // luodaan silmukka, joka iteroi koko taulukon läpi, x vastaa riviä ja y saraketta
        for (int x = erotus; x < kuvaKopio.length - erotus; x++) {
            for (int y = erotus; y < kuvaKopio[x].length - erotus; y++) {
                // jos keskellä on taustamerkki, jatketaan eteenpäin
                if (kuvaKopio[x][y] == merkit[0]) {
                    // tämä silmukka käy läpi kaikki vuorossa olevan taulukon alkion naapurit
                    for (int i = x - erotus; i <= erotus + x; i++) {
                        for (int j = y - erotus; j <= erotus + y; j++) {
                            // jos naapurista löytyy edustamerkki, lisätään edustamerkki
                            // kuvaan myös
                            if (kuvaKopio[i][j] == merkit[1]) {
                                kuva[x][y] = merkit[1];
                            }
                        }
                    }
                }
            }
        }
        return kuva;
    }
    
    
    
    // PÄÄOHJELMA
    public static void main(String[] args) {
        
        // tervehdysviesti
        System.out.println("-----------------------");
        System.out.println("| Binary image editor |");
        System.out.println("-----------------------");
        
        
        // luodaan error-ilmoitus, jos annetaan väärä määrä parametreja 
        // komentorivillä
        if (args.length == 0 || args.length > 2) {
            System.out.println("Invalid command-line argument!\nBye, see you soon.");
        }
        
        // huomioidaan myös, ettei toinen parametri voi olla mikään muu kuin echo
        else if (args.length == 2 && !args[1].equals("echo")) {
            System.out.println("Invalid command-line argument!\nBye, see you soon.");
        }
        
        else {
            // luodaan komentoriviparametrina saadusta tiedostosta kuva
            String tiedostonNimi = args[0];
            char[] merkit = new char[2];
            char[][] kuva = lataaKuvataulukkoon(tiedostonNimi, merkit);
            if (kuva == null) {
                System.out.println("Invalid image file!\nBye, see you soon.");
            }
            
            else {
                // luodaan scanner lukemaan syötteitä ja luodaan silmukka lukemaan
                // käyttäjän syötteitä, esitellään myös infoa ja dilaatiota/eroosiota 
                // varten leveys- ja pituusmuuttujat
                int korkeus = kuva.length;
                int leveys = kuva[0].length;
                Scanner lukija = new Scanner(System.in);
                
                while (true) {
                    // esitellään komennot käyttäjälle ja esitellään komento-muuttuja
                    System.out.println("print/info/invert/dilate/erode/load/quit?");
                    String komento = lukija.nextLine();
                
                    // kutsutaan eri metodeja riippuen käyttäjän komennosta
                    if (komento.equals(LOPETA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        System.out.println("Bye, see you soon.");
                        break;
                    }
                    else if (komento.equals(LATAA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        kuva = lataaKuvataulukkoon(tiedostonNimi, merkit);
                    }
                    else if (komento.equals(TULOSTA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        tulosta2d(kuva);
                    }
                    else if (komento.equals(INFO)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        // tulostetaan kuvan mitat ja merkkien määrät
                        System.out.println(korkeus + " x " + leveys);
                        
                        int[] määrät = laskeFrekvenssit(merkit, kuva);
                        System.out.println(merkit[0] + " " + määrät[0]);
                        System.out.println(merkit[1] + " " + määrät[1]);
                    }
                    else if (komento.equals(VAIHDA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        // vaihdetaan kuvan merkit päikseen
                        vaihdaMerkit(kuva, merkit);
                        // vaihdetaan myös merkit-taulukon merkit, jotta dilaatio/eroosio
                        // toimii oikein myös invertin jälkeen
                        vaihdaMerkit(merkit);
                        
                    }
                    else if (komento.contains(LAAJENNA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        // luodaan muuttuja komennon loppuosasta ja varaudutaan virheellisiin
                        // syötteisiin
                        try {
                            String[] osat = komento.split(" ");
                            int koko = Integer.parseInt(osat[1]);
                            // kutsutaan metodia ja laajennetaan kuvaa mikäli syöte on kunnossa
                            if (koko >= 3 && koko <= korkeus && koko <= leveys && koko % 2 != 0 && osat.length == 2) {
                                kuva = laajennaKuvaa(kuva, merkit, koko);
                            }
                            else {
                                System.out.println("Invalid command!");
                            }
                        }
                        catch (Exception e) {
                            System.out.println("Invalid command!");
                        }
                    }
                    else if (komento.contains(PIENENNA)) {
                        if (args.length == 2) {
                            System.out.println(komento);
                        }
                        // luodaan muuttuja komennon loppuosasta ja varaudutaan virheellisiin
                        // syötteisiin
                        try {
                            String[] osat = komento.split(" ");
                            int koko = Integer.parseInt(osat[1]);
                            
                            // kutsutaan metodia ja pienennetään kuvaa mikäli syöte on kunnossa
                            
                            if (koko >= 3 && koko <= korkeus && koko <= leveys && koko % 2 != 0 && osat.length == 2) {
                                // vaihdetaan merkit toisin päin, jotta laajennaKuvaa-metodi tekee
                                // dilaation sijaan eroosion
                                vaihdaMerkit(kuva, merkit);
                                kuva = laajennaKuvaa(kuva, merkit, koko);
                                
                                // vaihdetaan merkit takaisin
                                vaihdaMerkit(kuva, merkit);
                            }
                            else {
                                System.out.println("Invalid command!");
                            }
                        }
                        catch (Exception e) {
                            System.out.println("Invalid command!");
                        }
                    }
                    else {
                        System.out.println("Invalid command!");
                    }
                }
            }
        }
    }
}
        
        
        
        
        
        
        
        
        
        