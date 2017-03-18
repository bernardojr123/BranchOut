package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dados.Sessao;
import com.example.bernardojr.branchout.dados.UsuarioDAO;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.util.List;


public class MatchFragment extends Fragment {

    private ListView listContatos;
    private ListView listMatch;
    private ImageView imgRefreshContato;
    private ImageView imgRefreashMatch;
    private UsuariosAdapter contatosAdapter;
    private UsuariosAdapter solicitacoesAdapter;

    private Context context;

    private List<Usuario> contatos;
    private List<Usuario> solicitacoes;
    private Sessao sessao = Sessao.getInstancia();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match,container,false);

        listContatos = (ListView) view.findViewById(R.id.match_fragment_list_contatos);
        imgRefreshContato = (ImageView) view.findViewById(R.id.match_fragment_img_contatos);

        listMatch = (ListView) view.findViewById(R.id.match_fragment_list_solicitacoes);
        imgRefreashMatch = (ImageView) view.findViewById(R.id.match_fragment_img_solicitacoes);
        if(sessao.getUsuario()!= null){
            if (sessao.getUsuario().getContatos() != null){
                contatos = Sessao.getInstancia().getUsuario().getContatos();
                contatosAdapter = new UsuariosAdapter(getActivity(),contatos,true);
                listContatos.setAdapter(contatosAdapter);
            } if(sessao.getUsuario().getSolicitacoes() != null){
                solicitacoes = Sessao.getInstancia().getUsuario().getSolicitacoes();
                solicitacoesAdapter = new UsuariosAdapter(getActivity(),solicitacoes,false);
                listMatch.setAdapter(solicitacoesAdapter);
            }
        }

        imgRefreashMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaListagens();
            }
        });
        imgRefreshContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaListagens();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    private void atualizaListagens(){
        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity());
        usuarioDAO.pegaUsuario(Sessao.getInstancia().getUsuario().getEmail(),"1");
        contatos = Sessao.getInstancia().getUsuario().getContatos();
        solicitacoes = Sessao.getInstancia().getUsuario().getSolicitacoes();
        contatosAdapter = new UsuariosAdapter(getActivity(),contatos,true);
        listContatos.setAdapter(contatosAdapter);
        solicitacoesAdapter = new UsuariosAdapter(getActivity(),solicitacoes,false);
        listMatch.setAdapter(solicitacoesAdapter);
    }

    public static void carregaUsuario(Usuario usuario){
        Sessao.getInstancia().setUsuario(usuario);
    }


}
