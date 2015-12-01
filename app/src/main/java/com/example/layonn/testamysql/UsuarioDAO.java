package com.example.layonn.testamysql;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class UsuarioDAO {

    private static final String URL = "http://192.168.43.131:8080/ConectaAndroid/services/UsuarioDAO?wsdl";
    private static final String NAMESPACE = "http://androidconecta.com.br";

    private static final String INSERIR = "inserirUsuario";
    private static final String EXCLUIR = "exluirUsuario";
    private static final String ATUALIZAR = "atualizarUsuario";
    private static final String BUSCAR_TODOS = "buscarTodosUsuarios";
    private static final String BUSCAR_POR_ID = "buscarUsuarioPorId";

    public boolean inserirUsuario(Usuario usuario){
        SoapObject inserirUsuario = new SoapObject(NAMESPACE, INSERIR);

        //Criando um objeto complexo para ser enviado
        SoapObject usr = new SoapObject(NAMESPACE, "usuario");

        //Adicionando as propriedades do objeto
        usr.addProperty("id", usuario.getId());
        usr.addProperty("nome", usuario.getNome());
        usr.addProperty("idade", usuario.getIdade());

        //Adicionando o objeto usuario no método
        inserirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        }catch (HttpResponseException e){
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }catch (XmlPullParserException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean atualizarUsuario(Usuario usuario){

        return true;
    }

    public boolean excluirUsuario(Usuario usuario){

        return true;

    }

    public Usuario buscarUsuarioPorId(int id){

        Usuario usr = null;


        return usr;
    }


    public ArrayList<Usuario> buscarTodosUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        SoapObject buscarUsuarios = new SoapObject(NAMESPACE, BUSCAR_TODOS);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuarios);

        envelope.implicitTypes = true;


        HttpTransportSE http = new HttpTransportSE(URL);



        try {
            http.call("urn:" + BUSCAR_TODOS, envelope);

            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();

            for (SoapObject soapObject : resposta ) {
                Usuario usr = new Usuario();

                usr.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
                usr.setNome(soapObject.getProperty("nome").toString());
                usr.setIdade(Integer.parseInt(soapObject.getProperty("idade").toString()));

                lista.add(usr);
            }

        }catch (HttpResponseException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }catch (XmlPullParserException e){
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    //Sobrecarga
    public boolean excluirUsuario(int id){
        return excluirUsuario(new Usuario(id, "", 0));
    }

}
