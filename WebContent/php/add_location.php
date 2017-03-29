<?php
 
/*
 * Registra las coordenadas de una ubicacion..
 */
 
// array para la respuesta JSON
$response = array();
 
// validacion de campos requeridos
if (isset($_POST['NROCHASIS']) && isset($_POST['LATITUD']) && isset($_POST['LONGITUD']) && isset($_POST['FLAG'])) {
 
    $nrochasis = $_POST['NROCHASIS'];
    $latitud = $_POST['LATITUD'];
    $longitud = $_POST['LONGITUD'];
    $fechaHora = $_POST['FECHA_HORA'];
    $flag = $_POST['FLAG'];	
 
    // inclusion de la clase para conectar a la bd
    require_once __DIR__ . '/db_connect_waitingpiri.php';
 
    // conectando a la bd
    $db = new DB_CONNECT();
 
    // mysql insertando una nueva fila
    $result = mysql_query("INSERT INTO LOCALIZACIONES(NROCHASIS, LATITUD, LONGITUD, FLAG) VALUES('$nrochasis', $latitud, $longitud, $flag)");
 
    // verifica si la fila se inserto
    if ($result) {
        // correctamente insertado en la bd
        $response["success"] = 1;
        $response["message"] = "Localizacion correctamente agregada..";
 
        // enviando respuesta JSON
        echo json_encode($response);
    } else {
        // error al insertar la fila
        $response["success"] = 0;
        $response["message"] = "Error al tratar de insertar la fila";
 
        // enviando respuesta JSON 
        echo json_encode($response);
    }
} else {
    // faltan campos obligatorios
    $response["success"] = 0;
    $response["message"] = "Faltan campos obligatorios";
 
    // enviando respuesta JSON
    echo json_encode($response);
}
?>
