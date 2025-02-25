CREATE DATABASE IF NOT EXISTS Concesionario;
USE Concesionario;

CREATE TABLE IF NOT EXISTS coches (
                                      matricula VARCHAR(15) PRIMARY KEY,
                                      marca VARCHAR(50) NOT NULL,
                                      modelo VARCHAR(50) NOT NULL,
                                      color VARCHAR(30) NOT NULL,
                                      precio FLOAT(8) NOT NULL
);