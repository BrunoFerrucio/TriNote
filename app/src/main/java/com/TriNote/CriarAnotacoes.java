package com.TriNote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.Alerts.AlertaFontes;
import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Usuario.Dados;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class CriarAnotacoes extends AppCompatActivity implements AlertaFontes.SingleChoiceListener {
    public static final int Codigo_galeria = 2;
    String ID; //Guarda id da anotação
    String fonte = ""; //Guarda nome da fonte
    EditText Titulo, Nota;
    ImageView Image;
    AdapterAnotacoes anotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_anotacoes);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        Titulo = findViewById(R.id.edtNewNotaTitulo);
        Nota = findViewById(R.id.edtNewNotaConteudo);
        Image = findViewById(R.id.edtNewNotaImagem);
        Nota.requestFocus();

        detectarMudarFonte(); //Guarda nome da fonte

        if (this.getIntent().getSerializableExtra("anotacao") != null) {
            anotacoes = (AdapterAnotacoes) this.getIntent().getSerializableExtra("anotacao");
            exibiAnotacao();
        }

    }

    private void filtrosSalvamento() {
        if (Titulo.getText().toString().equals("") && Nota.getText().toString().equals("") && Image.getDrawable() == null) {
            Toast.makeText(this, "Anotação vazia excluida", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Anotacoes.class));
            finishAffinity();
        } //Nada preenchido
        else {
            salvarAnotacao();
        }
    } //Filtra se tem ou não conteudo para salvar

    private void salvarAnotacao() {
        //Inicializador retrofit = new Inicializador();
        DataBaseNotas sql = new DataBaseNotas(this);
        String titulo = Titulo.getText().toString();
        String conteudo = Nota.getText().toString();

        if (anotacoes != null) {
            AdapterAnotacoes anotacoes = new AdapterAnotacoes();
            anotacoes.setIdAnotacao(ID);
            anotacoes.setTitulo(titulo);
            anotacoes.setConteudo(conteudo);
            anotacoes.setFont(fonte);
            if (Image.getDrawable() != null) {
                Drawable drawable = Image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                byte[] imgByte = stream.toByteArray();
                anotacoes.setImagem(imgByte);
            }

           /* retrofit.servico().AtualizaNotas(Dados.getDadoId(), anotacoes).enqueue(new Callback<List<AdapterAnotacoes>>() {
                @Override
                public void onResponse(Call<List<AdapterAnotacoes>> call, Response<List<AdapterAnotacoes>> response) {
                    Toast.makeText(CriarAnotacoes.this, "Anotação atualizada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CriarAnotacoes.this, Anotacoes.class));
                    finishAffinity();
                }

                @Override
                public void onFailure(Call<List<AdapterAnotacoes>> call, Throwable t) {
                    Toast.makeText(CriarAnotacoes.this, "Erro na conexão", Toast.LENGTH_SHORT).show();
                }
            });*/

            sql.AtualizaAnotacao(anotacoes);
            startActivity(new Intent(CriarAnotacoes.this, Anotacoes.class));
            finishAffinity();
        } //Atualiza
        else {
            AdapterAnotacoes anotacoes = new AdapterAnotacoes();
            String id = UUID.randomUUID().toString();
            anotacoes.setIdAnotacao(id);
            anotacoes.setUsuarioCria(Dados.getDadoId());
            anotacoes.setTitulo(titulo);
            anotacoes.setConteudo(conteudo);
            anotacoes.setFont(fonte);
            anotacoes.setUsuarioCria(Dados.getDadoId());

            if (Image.getDrawable() != null) {
                Drawable drawable = Image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] imgByte = stream.toByteArray();
                anotacoes.setImagem(imgByte);
            }

            /*retrofit.servico().CriarAnotacao(Dados.getDadoId(), anotacoes).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(CriarAnotacoes.this, "Anotação salva", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CriarAnotacoes.this, Anotacoes.class));
                    finishAffinity();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CriarAnotacoes.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });*/

            sql.CriarAnotacao(anotacoes);
            startActivity(new Intent(CriarAnotacoes.this, Anotacoes.class));
            finishAffinity();
        } //Salva
    } //Salva ou atualiza anotação

    private void detectarMudarFonte() {
        if (!fonte.equals("")) {
            if (fonte.equals("amatic")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.amatic_sc);
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "amatic";
            } else if (fonte.equals("dancing")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.dancing_script);
                ;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "dancing";
            } else if (fonte.equals("pinyon")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.pinyon_script);
                ;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "pinyon";
            } else if (fonte.equals("press start")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.press_start_2p);
                ;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "press start";
            } else if (fonte.equals("sacramento")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.sacramento);
                ;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "sacramento";
            } else if (fonte.equals("tangerine")) {
                Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.tangerine);
                ;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
                fonte = "tangerine";
            } else {
                Typeface font = null;
                Titulo.setTypeface(font);
                Nota.setTypeface(font);
            } //Fonte padrão
        }
    } //Muda fonte quando anotação for editada

    private void exibiAnotacao() {
        ID = anotacoes.getIdAnotacao();
        fonte = anotacoes.getFont();
        detectarMudarFonte();
        Titulo.setText(anotacoes.getTitulo());
        Nota.setText(anotacoes.getConteudo());
        if (anotacoes.getImagem() != null) {
            byte[] imgByte = anotacoes.getImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            Image.setImageBitmap(bitmap);
        }
    } //Exibi anotação que vem do GridView

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_notas, menu);
        return true;
    } //infla menu

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                filtrosSalvamento();
                break; //Seta que aparece na barra superior

            case R.id.MenuAddImagem:
                receberTratarImagens();
                break; //Pega imagem da galeria

            case R.id.MenuAddNotaShare:
                compartilhar();
                break; //Compartilha conteudo para outros aplicativos

            case R.id.MenuAddNotaDelete:
                apagar();
                break; //Apaga anotação

            case R.id.MenuAddFonte:
                alertMudarFonte();
                break; //Muda fonte

            default:
                break;
        }
        return true;
    } //Ações de item do menu

    private void apagar() {
        //Inicializador retrofit = new Inicializador();
        DataBaseNotas sql = new DataBaseNotas(this);
        String titulo = Titulo.getText().toString();
        String conteudo = Nota.getText().toString();

        if (anotacoes != null) {
            //anotacoes.setUsuarioExclui(Dados.getDadoId());
            anotacoes.setIdAnotacao(ID);
            anotacoes.setTitulo(titulo);
            anotacoes.setConteudo(conteudo);

            if (Image.getDrawable() != null) {
                Drawable drawable = Image.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream);
                byte[] imgByte = stream.toByteArray();
                anotacoes.setImagem(imgByte);
            }
            /*retrofit.servico().ExcluirAnotacao(anotacoes.getIdAnotacao()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(CriarAnotacoes.this, "Anotação excluida", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CriarAnotacoes.this, Anotacoes.class));
                    finishAffinity();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CriarAnotacoes.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });*/

            sql.ExluirAnotacao(anotacoes);
            Intent troca = new Intent(CriarAnotacoes.this, Anotacoes.class);
            startActivity(troca);
        }
    } //Apaga anotação

    private void compartilhar() {
        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TITLE, Titulo.getText().toString());
        send.putExtra(Intent.EXTRA_TEXT,
                Titulo.getText().toString() + "\n" + Nota.getText().toString());
        send.setType("text/plain");

        Intent share = Intent.createChooser(send, "Enviar anotação");
        startActivity(share);
    } //Compartilha anotações com outros aplicativos

    private void receberTratarImagens() {
        if (ActivityCompat.checkSelfPermission(CriarAnotacoes.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent galeria = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galeria, Codigo_galeria);
        } else {
            String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(CriarAnotacoes.this, permission, 0);
        }
    } //Pega imagem da galeria

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        /*if (requestCode == Codigo_foto && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            assert extra != null;
            Bitmap miniaturaFoto = (Bitmap) extra.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert miniaturaFoto != null;
            miniaturaFoto.compress(Bitmap.CompressFormat.JPEG, 95, stream);
            byte[] imgByte = stream.toByteArray();
            Image.setImageBitmap(miniaturaFoto);
        } //Exibi imagem da camera salva*/

        if (requestCode == Codigo_galeria && resultCode == RESULT_OK) {
            Uri selecionaImg = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selecionaImg, filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } //Trata imagem galeria
    } //Resultado da ação de Camera ou galeria

    private void alertMudarFonte() {
        DialogFragment singleChoiceListener = new AlertaFontes();
        singleChoiceListener.setCancelable(false);
        singleChoiceListener.show(getSupportFragmentManager(), "Single");
    }

    public void onPositiveButtonClicked(String[] list, int position) {
        if (list[position].equals("Padrão")) {
            Typeface font = null;
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            fonte = "padrao";
        } else if (list[position].equals("Amatic")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.amatic_sc);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            fonte = "amatic";
        } else if (list[position].equals("Dancing")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.dancing_script);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            fonte = "dancing";
        } else if (list[position].equals("Pinyon")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.pinyon_script);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            Titulo.setTextSize(23);
            Nota.setTextSize(23);
            fonte = "pinyon";
        } else if (list[position].equals("Press start")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.press_start_2p);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            fonte = "press start";
        } else if (list[position].equals("Sacramento")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.sacramento);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            Titulo.setTextSize(23);
            Nota.setTextSize(23);
            fonte = "sacramento";
        } else if (list[position].equals("Tangerine")) {
            Typeface font = ResourcesCompat.getFont(CriarAnotacoes.this, R.font.tangerine);
            Titulo.setTypeface(font);
            Nota.setTypeface(font);
            Titulo.setTextSize(23);
            Nota.setTextSize(23);
            fonte = "tangerine";
        } else {
            Toast.makeText(this, "Erro ao mudar fonte para " + list[position] + "", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        filtrosSalvamento();
    } //Ação do botão de voltar nativo
}