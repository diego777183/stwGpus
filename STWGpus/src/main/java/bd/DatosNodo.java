/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author fserna
 */
@Entity
public class DatosNodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date fecha;

    private String name;
    private String efficiency;
    private String hashrate;
    private String powerGPU;
    private String temperature;
    private String solved_count;
    private String rejected_count;
    private String thermometer;
    private String power;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFecha() {
        fecha = new Date(System.currentTimeMillis());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }

    public void setHashrate(String hashrate) {
        this.hashrate = hashrate;
    }

    public void setPowerGPU(String powerGPU) {
        this.powerGPU = powerGPU;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setSolved_count(String solved_count) {
        this.solved_count = solved_count;
    }

    public void setRejected_count(String rejected_count) {
        this.rejected_count = rejected_count;
    }

    public void setThermometer(String thermometer) {
        this.thermometer = thermometer;
    }

    public void setPower(String power) {
        this.power = power;
    }
    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getName() {
        return name;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public String getHashrate() {
        return hashrate;
    }

    public String getPowerGPU() {
        return powerGPU;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSolved_count() {
        return solved_count;
    }

    public String getRejected_count() {
        return rejected_count;
    }

    public String getThermometer() {
        return thermometer;
    }

    public String getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "DatosNodo{" + "id=" + id + ", fecha=" + fecha + ", name=" + name + ", efficiency=" + efficiency + ", hashrate=" + hashrate + ", powerGPU=" + powerGPU + ", temperature=" + temperature + ", solved_count=" + solved_count + ", rejected_count=" + rejected_count + ", thermometer=" + thermometer + ", power=" + power + '}';
    }

  
    
}
