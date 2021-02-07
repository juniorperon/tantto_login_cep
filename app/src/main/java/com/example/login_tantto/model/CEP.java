package com.example.login_tantto.model;

public class CEP {

    //todas variaveis das informacoes existentes do viacep
    private String cep ="";
    private String logradouro ="";
    private String complemento ="";
    private String bairro="";
    private String localidade="";
    private String uf="";
    private String ibge="";
    private String gia="";
    private String ddd="";
    private String siafi="";

    //getters and setters
    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getSiafi() {
        return siafi;
    }

    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    //o que sera mostrado na tela
    @Override
    public String toString() {
        return "CEP: " + getCep()
                + "\nLogradouro: " + getLogradouro().replaceAll("([A-Z])", " $1")//replace para separar os textos juntos
                + "\nComplemento: " + getComplemento().replaceAll("([A-Z])", " $1")//replace para separar os textos juntos
                + "\nBairro: " + getBairro().replaceAll("([A-Z])", " $1")//replace para separar os textos juntos
                + "\nCidade:" + getLocalidade().replaceAll("([A-Z])", " $1")//replace para separar os textos juntos
                + "\nEstado: " + getUf()
                + "\nIBGE: " + getIbge()
                + "\nGia: " + getGia()
                + "\nDDD:" + getDdd()
                + "\nSiafi: " + getSiafi();
    }

}
