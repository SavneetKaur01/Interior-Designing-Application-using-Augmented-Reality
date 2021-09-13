package com.example.visualdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    private ModelRenderable tvRenderable,deskRenderable,couchRenderable, closetRenderable,lampRenderable;

    ImageView tv, desk,couch,closet,lamp;

    View arrayView[];
    ViewRenderable name_furniture;

    int selected=1;//Default tv is chosen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        tv=(ImageView)findViewById(R.id.tv);
        desk=(ImageView)findViewById(R.id.desk);
        couch=(ImageView)findViewById(R.id.couch);
        closet=(ImageView)findViewById(R.id.closet);
        lamp=(ImageView)findViewById(R.id.lamp);

        setArrayView();
        setClickListener();
        setupModel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //When user taps on plane,we will add model

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                createModel(anchorNode, selected);
            }
        });



    }

    private void setupModel() {
        ModelRenderable.builder().setSource(this, Uri.parse("tv.sfb"))
                .build().thenAccept(renderable -> tvRenderable=renderable)
                .exceptionally(throwable-> {
                            Toast.makeText(this, "Unable to load desk ", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder().setSource(this, Uri.parse("desk.sfb"))
                .build().thenAccept(renderable -> deskRenderable=renderable)
                .exceptionally(throwable-> {
                            Toast.makeText(this, "Unable to load closet ", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder().setSource(this, Uri.parse("untitled.sfb"))
                .build().thenAccept(renderable -> couchRenderable=renderable)
                .exceptionally(throwable-> {
                            Toast.makeText(this, "Unable to load lamp ", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder().setSource(this, Uri.parse("closet.sfb"))
                .build().thenAccept(renderable -> closetRenderable=renderable)
                .exceptionally(throwable-> {
                            Toast.makeText(this, "Unable to load closet ", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder().setSource(this, Uri.parse("lamp.sfb"))
                .build().thenAccept(renderable -> lampRenderable=renderable)
                .exceptionally(throwable-> {
                            Toast.makeText(this, "Unable to load lamp ", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );


    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected==1)
        {
            TransformableNode tv=new TransformableNode(arFragment.getTransformationSystem());
            tv.setParent(anchorNode);
            tv.setRenderable(tvRenderable);
            tv.select();
        }
        if (selected==2)
        {
            TransformableNode desk=new TransformableNode(arFragment.getTransformationSystem());
            desk.setParent(anchorNode);
            desk.setRenderable(deskRenderable);
            desk.select();
        }
        if (selected==3)
        {
            TransformableNode couch=new TransformableNode(arFragment.getTransformationSystem());
            couch.setParent(anchorNode);
            couch.setRenderable(couchRenderable);
            couch.select();
        }
        if (selected==4)
        {
            TransformableNode closet=new TransformableNode(arFragment.getTransformationSystem());
            closet.setParent(anchorNode);
            closet.setRenderable(closetRenderable);
            closet.select();
        }
        if (selected==5)
        {
            TransformableNode lamp=new TransformableNode(arFragment.getTransformationSystem());
            lamp.setParent(anchorNode);
            lamp.setRenderable(lampRenderable);
            lamp.select();
        }
    }

    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++)
            arrayView[i].setOnClickListener(this);
    }

    private void setArrayView() {
        arrayView=new View[]{
                tv,desk,couch,closet,lamp
        };

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv) {
            selected = 1;
            setBackground(v.getId());
        }
        else if (v.getId()==R.id.desk){
            selected=2;
            setBackground(v.getId());
        }
        else if(v.getId()==R.id.couch) {
            selected = 3;
            setBackground(v.getId());
        }
        else if(v.getId()==R.id.closet) {
            selected = 4;
            setBackground(v.getId());
        }
        else if(v.getId()==R.id.lamp) {
            selected = 5;
            setBackground(v.getId());
        }
    }

    private void setBackground(int id) {
        for(int i=0;i<arrayView.length;i++)
        {

            if (arrayView[i].getId()==id)
                arrayView[i].setBackgroundColor(Color.parseColor("#80333639"));//Set background for select furniture
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
