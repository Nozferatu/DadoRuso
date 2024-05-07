package main;

import clases.*;
import flojerasdependency.FlojerasUtility;

public class Main extends FlojerasUtility {

    public static Objeto objetoAUsar = null;
    public static final int VIDAS = 9;
    
    public static void main(String[] args) {

        String titulo="                                                                                                                  \n" +
        "                                             ,----..                                                   ,----..    \n" +
        "    ,---,       ,---,           ,---,       /   /   \\          ,-.----.                  .--.--.      /   /   \\   \n" +
        "  .'  .' `\\    '  .' \\        .'  .' `\\    /   .     :         \\    /  \\           ,--, /  /    '.   /   .     :  \n" +
        ",---.'     \\  /  ;    '.    ,---.'     \\  .   /   ;.  \\        ;   :    \\        ,'_ /||  :  /`. /  .   /   ;.  \\ \n" +
        "|   |  .`\\  |:  :       \\   |   |  .`\\  |.   ;   /  ` ;        |   | .\\ :   .--. |  | :;  |  |--`  .   ;   /  ` ; \n" +
        ":   : |  '  |:  |   /\\   \\  :   : |  '  |;   |  ; \\ ; |        .   : |: | ,'_ /| :  . ||  :  ;_    ;   |  ; \\ ; | \n" +
        "|   ' '  ;  :|  :  ' ;.   : |   ' '  ;  :|   :  | ; | '        |   |  \\ : |  ' | |  . . \\  \\    `. |   :  | ; | ' \n" +
        "'   | ;  .  ||  |  ;/  \\   \\'   | ;  .  |.   |  ' ' ' :        |   : .  / |  | ' |  | |  `----.   \\.   |  ' ' ' : \n" +
        "|   | :  |  ''  :  | \\  \\ ,'|   | :  |  ''   ;  \\; /  |        ;   | |  \\ :  | | :  ' ;  __ \\  \\  |'   ;  \\; /  | \n" +
        "'   : | /  ; |  |  '  '--'  '   : | /  ;  \\   \\  ',  /         |   | ;\\  \\|  ; ' |  | ' /  /`--'  / \\   \\  ',  /  \n" +
        "|   | '` ,/  |  :  :        |   | '` ,/    ;   :    /          :   ' | \\.':  | : ;  ; |'--'.     /   ;   :    /   \n" +
        ";   :  .'    |  | ,'        ;   :  .'       \\   \\ .'           :   : :-'  '  :  `--'   \\ `--'---'     \\   \\ .'    \n" +
        "|   ,.'      `--''          |   ,.'          `---`             |   |.'    :  ,      .-./               `---`      \n" +
        "'---'                       '---'                              `---'       `--`----'                              \n" +
        "                                                                                                                  ";
        
        //Mínimo recomendado de vidas = 9
        System.out.println("Bienvenido al Juego del DADO RUSO");
        System.out.println(titulo);
        System.out.println("Dime tu nombre");
        String nom = pedirTexto();
        Humano J1 = new Humano(nom, VIDAS);
        
        PC bot = new PC("Acólito del aguacate", VIDAS);
        
        while(true){
            objetoAUsar = null;
            
            int turnj1 = J1.tirarDado();
            int turnbot = bot.tirarDado();
            System.out.println("Tu dado ha sacado un "+turnj1+" El dado de " + bot.getNombre() + " ha sacado un "+turnbot);
            
            if(turnj1>turnbot){     
                //Turno jugador
                System.out.println("Has sacado más que el bot, tienes el turno");
                
                if (turnj1>=5 && turnj1<=6) {   
                    System.out.println("Tu dado ha sido por encima de 4, te ganas un objeto");
                    J1.aniadirObjeto(generarObjeto());
                }
                
                if (devolverNumEspaciosOcupados(J1.getInventario()) > 0) {
                    J1.turno(true);
                    usarObj(objetoAUsar,J1,bot);
                } else J1.turno(false);
                
                bot.dsiminuirVida();                        
            }else{
                //Turno bot
                if (turnj1>=5 && turnj1<=6) {    
                    bot.aniadirObjeto(generarObjeto());
                }
                
                if (devolverNumEspaciosOcupados(J1.getInventario()) > 0) {
                    bot.turno(true);
                    usarObj(objetoAUsar,bot,J1);
                } else bot.turno(false);
                
                J1.dsiminuirVida();
            }
            //Información de la ronda
            System.out.println("El bot " + bot.getNombre() + " tiene " + bot.getVidas());
            System.out.println("El jugador " + J1.getNombre() + " tiene " + J1.getVidas());
            
            if(J1.getVidas() <= 0 || bot.getVidas() <= 0) {
                System.out.println("Partida terminada");
                break;
            }
            System.out.println("Ronda terminada - Presione Enter para pasar a la siguiente");
            sc.nextLine();
        }
        
        if(J1.getVidas() <= 0){
            System.out.println("Ha ganado el bot " + bot.getNombre());
        }
        
        if(bot.getVidas() <= 0){
            System.out.println("Ha ganado el jugador " + J1.getNombre());
        }
    }
    
    public static Objeto generarObjeto(){
        Objeto o;
        int random = randomInt(1, 100);
        
        if(random >= 1 && random <= 45){
            o = new Objeto(TipoObjeto.AGUACATE);
        }else if(random >= 46 && random <= 89){
            o = new Objeto(TipoObjeto.DARDO);
        }else{
            o = new Objeto(TipoObjeto.DARDO_DORADO);
        }
        
        return o;
    }
    
    public static void usarObj(Objeto x, Jugador y, Jugador z){
        if (x == null) {
            
        } else if (x.getNombre() == TipoObjeto.AGUACATE) {
            y.aumentarVida();
        } else if (x.getNombre() == TipoObjeto.DARDO) {
            z.dsiminuirVida();
        } else if (x.getNombre() == TipoObjeto.DARDO_DORADO) {
            z.dsiminuirVida();
            z.dsiminuirVida();
        }
    }
}
