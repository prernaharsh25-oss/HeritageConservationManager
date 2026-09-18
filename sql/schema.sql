-- schema.sql
-- Database schema for the Heritage Conservation Manager.
-- Run this once in MySQL before starting the application.

CREATE DATABASE IF NOT EXISTS heritage_conservation;
USE heritage_conservation;

CREATE TABLE IF NOT EXISTS heritage_sites (
    site_id            VARCHAR(20)  PRIMARY KEY,
    name               VARCHAR(100) NOT NULL,
    location           VARCHAR(100) NOT NULL,
    type               VARCHAR(50)  NOT NULL,
    historical_period  VARCHAR(100),
    condition          VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS inspections (
    inspection_id      VARCHAR(20)  PRIMARY KEY,
    site_id            VARCHAR(20)  NOT NULL,
    inspector_name     VARCHAR(100) NOT NULL,
    inspection_date    DATE         NOT NULL,
    condition          VARCHAR(30),
    issues_found       TEXT,
    remarks            TEXT,
    FOREIGN KEY (site_id) REFERENCES heritage_sites(site_id)
);

CREATE TABLE IF NOT EXISTS conservation_tasks (
    task_id            VARCHAR(20)  PRIMARY KEY,
    site_id            VARCHAR(20)  NOT NULL,
    issue              VARCHAR(200) NOT NULL,
    action             VARCHAR(200) NOT NULL,
    priority           VARCHAR(20),
    status             VARCHAR(30),
    assigned_to        VARCHAR(100),
    FOREIGN KEY (site_id) REFERENCES heritage_sites(site_id)
);
