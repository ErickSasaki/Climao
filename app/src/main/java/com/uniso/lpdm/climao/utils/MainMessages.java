package com.uniso.lpdm.climao.utils;

import com.uniso.lpdm.climao.R;
import com.uniso.lpdm.climao.weather.Main;
import com.uniso.lpdm.climao.weather.Weather;

import java.util.Calendar;

public class MainMessages {

    public static String[] getMessages(Weather weather, Main main) {

        String weatherMain = weather.getMain();
        String description = weather.getDescription();
        Boolean hot = false;

        if (Converter.kelvinToCelcius(main.getTemp()) > 20) {
            hot = true;
        }


        Boolean sol = true;

        // Usa a classe Calendar pra pegar a Hora atual.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Caso seja noite define sol = false, usado para trocar icone de sol para lua.
        if (hour < 6 && hour > 19 ) {
            sol = false;
        }

        if (weatherMain.equals("Clear") && sol) {

            if (hot) {
                String messages[] = {
                        "Sol tá torrando, pega um óculos de sol.",
                        "Protetor solar seria bom hein man.",
                        "Boné pra não esquentar a cabeça cai bem.",
                        "Pega a garrafinha, ninguém quer ficar desidratado com esse sol.",
                };
                return messages;
            } else {
                String messages[] = {
                        "Protetor solar é bom mesmo no friozin.",
                        "Pega a garrafinha, ninguém quer ficar desidratado com esse sol.",
                        "Não esquece os óculos de sol, bem mandrake.",
                        "",
                };
                return messages;
            }
        }

        if (weatherMain.equals("Clear")) {

            if (hot) {
                String messages[] = {
                        "Céu limpo, dia bom para observar as estrelas a noite.",
                        "Nem se preocupa com o guarda-chuva.",
                        "Se for beber, não dirija.",
                        "Dá pra sair até de chinelo hoje.",
                };
                return messages;
            } else {
                String messages[] = {
                        "Vai tá show pra ver a lua hoje, só pega um casaco antes.",
                        "Cheirinho de filme com pipoca, só agradece.",
                        "Se for beber, não dirija.",
                        "Céu limpo, dia bom para observar as estrelas à noite.",
                };
                return messages;
            }

        }

        if (weatherMain.equals("Clouds")) {

            if (description.equals("few clouds")) {
                if (sol) {

                    if (hot) {
                        String messages[] = {
                                "Sol tá torrando, pega um óculos de sol.",
                                "Protetor solar seria bom hein man.",
                                "Enche a garrafinha aí, ninguém quer ficar desidratado com esse sol.",
                                ""
                        };
                        return messages;
                    } else {
                        String messages[] = {
                                "Lembra que esse sol também queima hein.",
                                "Enche a garrafinha aí, ninguém quer ficar desidratado com esse sol.",
                                "Pega um óculos de sol.",
                                ""
                        };
                        return messages;
                    }

                } else {

                    if (hot) {
                        String messages[] = {
                                "Hoje é o dia pra dar aquele rolê a céu aberto.",
                                "Se for beber, não dirija.",
                                "Chinelinho e boa.",
                                "",
                        };
                        return messages;
                    } else {
                        String messages[] = {
                                "Meia com chinelo, pra ficar bem mandrake.",
                                "Se for beber, não dirija.",
                                "Se for sair, o ideal seria uma jaqueta corta vento.",
                                "Em casa, aquele moletom de sempre e uns filmes.",
                        };
                        return messages;
                    }

                }
            }

            else if (description.equals("scattered clouds")) {
                if (sol) {

                    if (hot) {
                        String messages[] = {
                                "Na dúvida, deixa um guarda-chuva na bolsa",
                                "Não é porque tá nublado que você vai deixar de beber água, né?",
                                "Mesmo nublado é sempre bom passar o protetor solar.",
                                "",
                        };
                        return messages;
                    } else {
                        String messages[] = {
                                "Na dúvida, deixa um guarda-chuva na bolsa",
                                "Não é porque tá nublado que você vai deixar de beber água, né?",
                                "Hoje um moletom, ou blusa bem quentinha, vai bem.",
                                "",
                        };
                        return messages;
                    }

                } else {

                    if (hot) {
                        String messages[] = {
                                "Na dúvida, deixa um guarda-chuva na bolsa.",
                                "Se for beber, não dirija.",
                                "Hoje não vai rolar de ver as estrelas não, hein meu chapa.",
                                "",
                        };
                        return messages;
                    } else {
                        String messages[] = {
                                "Se for beber, não dirija.",
                                "Hoje não vai rolar de ver as estrelas não, hein meu chapa.",
                                "Investe no chá hoje.",
                                "Um moletom, ou blusa bem quentinha, vai bem.",
                        };
                        return messages;
                    }

                }
            }

        }

        if ((weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) && sol) {

            if (hot) {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "Chuva é bom para lembrar de tomar água.",
                        "",
                        "",
                };
                return messages;

            } else {
                String messages[] = {
                        "O tempo está uó, mas não deixa de tomar água.",
                        "Aquela blusa quentinha se for sair.",
                        "Investe no chá hoje.",
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                };
                return messages;
            }

        }

        if (weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) {

            if (hot) {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "Investe no chá hoje.",
                        "Se for beber, não dirija.",
                        "",
                };
                return messages;
            } else {
                String messages[] = {
                        "Se for sair, uma blusa vai bem.",
                        "Investe no chá hoje.",
                        "Garoazinha boa pra dormir.",
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                };
                return messages;
            }

        }

        if (weatherMain.equals("Thunderstorm") && sol) {
            if (hot) {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "O tempo está uó, mas não deixa de tomar água.",
                        "Não coloca roupa que esquente muito, apesar da chuva, vai fazer calor",
                        "",
                };
                return messages;
            } else {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "Não deixa roupa no varal nem janela aberta.",
                        "Investe no chá hoje.",
                        "",
                };
                return messages;
            }
        }

        if (weatherMain.equals("Thunderstorm")) {
            if (hot) {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "Chuva é bom para lembrar de tomar água.",
                        "Que tal um filminho com pipoca?",
                        "",
                };
                return messages;
            } else {
                String messages[] = {
                        "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
                        "Se for sair, cuidado ao atravessar a rua.",
                        "Investe no chá hoje.",
                        "Se for ficar em casa, escolhe um filme massa que essa chuva vai ser boa.",
                };
                return messages;
            }
        }

        String messages[] = {
                "Se for sair, uma blusa vai bem.",
                "Investe no chá hoje.",
                "Garoazinha boa pra dormir.",
                "Guarda-chuvinha para não molhar a cabeça, ou aquela capa chavosa.",
        };
        return messages;

    }

    /**
     * Recebe os dados da api e retorna os icones correspondentes as mensagens.
     * @param weather Dados do clima
     * @param main Dados de temperatura e umidade.
     * @return Icone
     */
    public static int[] getMessageIcon(Weather weather, Main main) {

        String weatherMain = weather.getMain();
        String description = weather.getDescription();
        Boolean hot = false;

        if (Converter.kelvinToCelcius(main.getTemp()) > 20) {
            hot = true;
        }


        Boolean sol = true;

        // Usa a classe Calendar pra pegar a Hora atual.
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        // Caso seja noite define sol = false, usado para trocar icone de sol para lua.
        if (hour < 6 && hour > 19 ) {
            sol = false;
        }

        if (weatherMain.equals("Clear") && sol) {

            if (hot) {
                int icons[] = {
                        R.drawable.oculos,
                        R.drawable.protetor,
                        R.drawable.bone,
                        R.drawable.agua,
                };
                return icons;
            } else {
                int icons[] = {
                        R.drawable.protetor,
                        R.drawable.agua,
                        R.drawable.oculos,
                        0,
                };
                return icons;
            }
        }

        if (weatherMain.equals("Clear")) {

            if (hot) {
                int icons[] = {
                        R.drawable.estrela,
                        R.drawable.andar,
                        R.drawable.estrela,
                        R.drawable.chinelo,
                };
                return icons;
            } else {
                int icons[] = {
                        R.drawable.luazinha,
                        R.drawable.pipoca,
                        R.drawable.andar,
                        R.drawable.estrela,
                };
                return icons;
            }

        }

        if (weatherMain.equals("Clouds")) {

            if (description.equals("few clouds")) {
                if (sol) {

                    if (hot) {
                        int icons[] = {
                                R.drawable.oculos,
                                R.drawable.protetor,
                                R.drawable.agua,
                                0,
                        };
                        return icons;
                    } else {
                        int icons[] = {
                                R.drawable.protetor,
                                R.drawable.agua,
                                R.drawable.oculos,
                                0,
                        };
                        return icons;
                    }

                } else {

                    if (hot) {
                        int icons[] = {
                                R.drawable.andar,
                                R.drawable.andar,
                                R.drawable.chinelo,
                                0,
                        };
                        return icons;
                    } else {
                        int icons[] = {
                                R.drawable.chinelo,
                                R.drawable.andar,
                                R.drawable.blusa,
                                R.drawable.blusa,
                        };
                        return icons;
                    }

                }
            }

            else if (description.equals("scattered clouds")) {
                if (sol) {

                    if (hot) {
                        int icons[] = {
                                R.drawable.guarda_chuva,
                                R.drawable.agua,
                                R.drawable.protetor,
                                0,
                        };
                        return icons;
                    } else {
                        int icons[] = {
                                R.drawable.guarda_chuva,
                                R.drawable.agua,
                                R.drawable.blusa,
                                0,
                        };
                        return icons;
                    }

                } else {

                    if (hot) {
                        int icons[] = {
                                R.drawable.guarda_chuva,
                                R.drawable.andar,
                                R.drawable.estrela,
                                0,
                        };
                        return icons;
                    } else {
                        int icons[] = {
                                R.drawable.andar,
                                R.drawable.estrela,
                                R.drawable.cha,
                                R.drawable.blusa,
                        };
                        return icons;
                    }

                }
            }

        }

        if ((weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) && sol) {

            if (hot) {
                int icons[] = {
                        R.drawable.blusa,
                        R.drawable.agua,
                        0,
                        0,
                };
                return icons;

            } else {
                int icons[] = {
                        R.drawable.agua,
                        R.drawable.blusa,
                        R.drawable.cha,
                        R.drawable.capa_chuva,
                };
                return icons;
            }

        }

        if (weatherMain.equals("Rain") || weatherMain.equals("Drizzle")) {

            if (hot) {
                int icons[] = {
                        R.drawable.capa_chuva,
                        R.drawable.cha,
                        R.drawable.andar,
                        0,
                };
                return icons;
            } else {
                int icons[] = {
                        R.drawable.blusa,
                        R.drawable.cha,
                        R.drawable.dormir,
                        R.drawable.capa_chuva,
                };
                return icons;
            }

        }

        if (weatherMain.equals("Thunderstorm") && sol) {
            if (hot) {
                int icons[] = {
                        R.drawable.capa_chuva,
                        R.drawable.agua,
                        R.drawable.blusa,
                        0,
                };
                return icons;
            } else {
                int icons[] = {
                        R.drawable.capa_chuva,
                        R.drawable.blusa,
                        R.drawable.cha,
                        0,
                };
                return icons;
            }
        }

        if (weatherMain.equals("Thunderstorm")) {
            if (hot) {
                int icons[] = {
                        R.drawable.capa_chuva,
                        R.drawable.agua,
                        R.drawable.pipoca,
                        0,
                };
                return icons;
            } else {
                int icons[] = {
                        R.drawable.capa_chuva,
                        R.drawable.andar,
                        R.drawable.cha,
                        R.drawable.pipoca,
                };
                return icons;
            }
        }

        int icons[] = {
                R.drawable.blusa,
                R.drawable.cha,
                R.drawable.dormir,
                R.drawable.capa_chuva,
        };
        return icons;

    }

}
