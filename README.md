# ARSW - Parcial T2 Tic-tac-toe - BACK

El backend funciona como un servidor que mezcla varias arquitecturas, tenemos un sistema de eventos publicador-suscriptor mediante un broker de mensajería webSocket configurado en el archivo WebSocketConfig. En este establecemos nuestro endpoint principal "/ws/tictactoe" que es el que usaremos en nuestro frontend para conectarnos via webSocket.

También funcionará como un intermediario entre los dos clientes que se conectarán, recibimos mensajes de uno y lo publicamos al otro. Y así sucesivamente a lo largo del juego.

De modelo de datos tenemos el jugador, el mensaje que es el que usaremos para el sistema de eventos mediante mensajería y las salas para agrupar de a dos jugadores, este sistema lo manejamos directamente en el servicio de nuestro juego en los metodos de crear sala, y unirse a una sala, en estos mismos métodos le asignaremos a cada jugador su respectivo simbolo con el que jugará.

```java
    public Room createRoom(String playerName) {
        String roomId = UUID.randomUUID().toString().substring(0, 8);
        String playerId = "p1";
        
        Room room = new Room(roomId);
        Player player = new Player(playerId, playerName, "X");
        room.addPlayer(player);
        
        rooms.put(roomId, room);
        return room;
    }

    public Room joinRoom(String roomId, String playerName) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        if (room.isFull()) {
            throw new IllegalStateException("Room is full");
        }

        String playerId = "p2";
        Player player = new Player(playerId, playerName, "O");
        room.addPlayer(player);
        
        return room;
    }
```

Estos simbolos funcionan perfecto en nuestro front.

Nuestro controlador expone endpoints tanto para crear sala como para unirse a una.

## Ejecución

Para ejecutar el juego ver las instrucciones del frontend:

[https://github.com/Juan-Jose-D/ARSW-Parcial-T2](https://github.com/Juan-Jose-D/ARSW-Parcial-T2)

## Autor

Juan José Díaz Gómez
