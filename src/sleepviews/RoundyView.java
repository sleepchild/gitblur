package sleepviews;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.graphics.*;
import sleepchild.gitblur.R;
import android.content.res.*;
import android.view.*;
import android.widget.*;

public class RoundyView extends LinearLayout
{
    public RoundyView(Context ctx){
        super(ctx);
        init();
    }

    public RoundyView(Context ctx, AttributeSet attrs){
        this(ctx, attrs,0);
        init();
    }

    public RoundyView(Context ctx, AttributeSet attrs, int defStyles){
        super(ctx, attrs, defStyles);
        getattribs(attrs);
        init();
    }
    //////
    
    int backgroundColor = Color.TRANSPARENT;
    int borderColor = Color.TRANSPARENT;
    int borderWidth=5;
    int cornerRadius=20;
    float radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight ;
    float[] cornerRadii = new float[8];
    boolean useRadii=false;
    
    private void init(){
        //
        apply();
    }
    
    private void getattribs(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewItem,0,0);
        //
        backgroundColor = a.getColor(R.styleable.CustomViewItem_backgroundColor, backgroundColor);
        borderColor = a.getColor(R.styleable.CustomViewItem_borderColor, borderColor);
        borderWidth = a.getDimensionPixelSize(R.styleable.CustomViewItem_borderWidth, borderWidth);
        cornerRadius = a.getDimensionPixelSize(R.styleable.CustomViewItem_cornerRadius, cornerRadius);
        //
    }

    @Override
    public void setBackgroundColor(int color)
    {
       // super.setBackgroundColor(color);
        backgroundColor = color;
        apply();
    }
    
    public void setBorderColor(int color){
        borderColor = color;
        apply();
    }
    
    public void setBorderWidth(int width){
        borderWidth = width;
        apply();
    }
    
    public void setCornerRadius(int radius){
        cornerRadius = radius;
        useRadii=false;
        apply();
    }
    
    public void setCornerRadii(float[] radii){
        cornerRadii = radii;
        useRadii=true;
        apply();
    }
    
    private void apply(){
        GradientDrawable gd = new GradientDrawable();
        
        if(useRadii){
            gd.setCornerRadii(cornerRadii);
        }else{
            gd.setCornerRadius(cornerRadius); 
        }
        gd.setColor(backgroundColor);
        gd.setStroke(borderWidth, borderColor);
        setBackground(gd);
    }
    
}
