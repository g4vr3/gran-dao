CREATE DATABASE IF NOT EXISTS Concesionario;
USE Concesionario;

CREATE TABLE IF NOT EXISTS coche (
                                      matricula VARCHAR(7) PRIMARY KEY,
                                      marca VARCHAR(50) NOT NULL,
                                      modelo VARCHAR(50) NOT NULL,
                                      color VARCHAR(30) NOT NULL,
                                      precio FLOAT(8) NOT NULL
);