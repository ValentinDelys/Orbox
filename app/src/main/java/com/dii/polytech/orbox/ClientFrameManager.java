package com.dii.polytech.orbox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientFrameManager {

    public boolean transmissionSucceed(String frame){

        if(frame.substring(0, 1).equals("1")){
            return true;
        }
        else{
            return false;
        }
    }

    private List<String> parseFrame(String frame){
        List<String> splitFrame = new ArrayList<String>();
        if(frame.indexOf("\t") != -1){
            splitFrame = (List<String>) Arrays.asList(frame.split("\t"));
        }
        else {
            splitFrame.add(frame);
        }
        return splitFrame;
    }


    public String getSimpleResult(String frame){
        List<String> listStr = parseFrame(frame);
        if(listStr.size() == 2){
            return listStr.get(1);
        }
        else {
            return null;
        }
    }

    public ArrayList<Category> buildCategory(String frame){
        ArrayList<Category> listCat = new ArrayList<Category>();
        List<String> listStr = parseFrame(frame);

        for(String str : listStr){
            if(!(str.equals("1")) && !(str.equals("0"))){
                Category newCat = parseCategorySegment(str);
                listCat.add(newCat);
            }
        }

        return listCat;
    }

    public ArrayList<ObjectOrbox> buildObjectOrbox(String frame){
        ArrayList<ObjectOrbox> listObj = new ArrayList<ObjectOrbox>();
        List<String> listStr = parseFrame(frame);

        for(String str : listStr){
            if(!(str.equals("1")) && !(str.equals("0"))){
                ObjectOrbox newObj = parseObjectOrboxSegment(str);
                listObj.add(newObj);
            }
        }

        return listObj;
    }

    public ArrayList<Descriptor> buildDescriptors(String frame){

        ArrayList<Descriptor> listDesc = new ArrayList<Descriptor>();
        List<String> listStr = parseFrame(frame);

        for(String str : listStr){
            if(!(str.equals("1")) && !(str.equals("0"))){

                Descriptor newDesc = parseDescriptorSegment(str);
                listDesc.add(newDesc);
            }
        }

        return listDesc;
    }

    public Parameter buildParameter(String frame){
        List<String> listStr = parseFrame(frame);
        return parseParameterSegment(listStr.get(1));
    }


    private Category parseCategorySegment(String segment){
        List<String> listElement = new ArrayList<String>();
        boolean nameOk = false;
        Category newCat = new Category();
        listElement = (List<String>) Arrays.asList(segment.split(";"));

        for(String str : listElement){

            String[] temp = str.split("=");
            temp[1] = temp[1].replace("\n", "");
            switch(temp[0]){
                case "name" :
                    newCat.set_name(temp[1]);
                    nameOk = true;
                    break;
            }
        }

        if(nameOk){
            return newCat;
        }
        else {
            return null;
        }
    }

    private ObjectOrbox parseObjectOrboxSegment(String segment){
        List<String> listElement = new ArrayList<String>();
        boolean nameOk = false, oralDescOk = false;
        ObjectOrbox newObj = new ObjectOrbox();
        listElement = (List<String>) Arrays.asList(segment.split(";"));

        for(String str : listElement){

            String[] temp = str.split("=");
            temp[1] = temp[1].replace("\n", "");
            switch(temp[0]){
                case "name" :
                    newObj.set_name(temp[1]);
                    nameOk = true;
                    break;
                case "oralDesc" :
                    newObj.set_name(temp[1]);
                    oralDescOk = true;
                    break;
            }
        }

        if(nameOk && oralDescOk){
            return newObj;
        }
        else {
            return null;
        }
    }

    private Descriptor parseDescriptorSegment(String segment){
        List<String> listElement = new ArrayList<String>();
        boolean nameOk = false, activeOk = false, calculatedOk = false, vignetteOk=false, xOk = false, yOk = false, widthOk = false, heighOk = false;
        Descriptor newDesc = new Descriptor();
        listElement = (List<String>) Arrays.asList(segment.split(";"));

        for(String str : listElement){

            String[] temp = str.split("=");
            temp[1] = temp[1].replace("\n", "");
            switch(temp[0]){
                case "name" :
                    newDesc.set_name(temp[1]);
                    nameOk = true;
                    break;
                case "active" :
                    newDesc.set_active(Boolean.getBoolean(temp[1]));
                    activeOk = true;
                    break;
                case "calculated" :
                    newDesc.set_descriptorCalculated(Boolean.getBoolean(temp[1]));
                    calculatedOk = true;
                    break;
                case "vignette" :
                    newDesc.set_vignetteCreated(Boolean.getBoolean(temp[1]));
                    vignetteOk = true;
                    break;
                case "x" :
                    newDesc.set_x(Integer.valueOf(temp[1]));
                    xOk = true;
                    break;
                case "y" :
                    newDesc.set_y(Integer.valueOf(temp[1]));
                    yOk = true;
                    break;
                case "width" :
                    newDesc.set_width(Integer.valueOf(temp[1]));
                    widthOk = true;
                    break;
                case "heigh" :
                    newDesc.set_height(Integer.valueOf(temp[1]));
                    heighOk = true;
                    break;
            }
        }

        if(nameOk && activeOk && calculatedOk && vignetteOk && xOk && yOk && widthOk && heighOk){
            return newDesc;
        }
        else {
            return null;
        }
    }

    private Parameter parseParameterSegment(String segment){
        List<String> listElement = new ArrayList<String>();
        boolean languageOk = false, soundOk = false, lightOk = false;
        Parameter newParam = new Parameter();
        listElement = (List<String>) Arrays.asList(segment.split(";"));

        for(String str : listElement){

            String[] temp = str.split("=");
            temp[1] = temp[1].replace("\n", "");
            switch(temp[0]){
                case "language" :
                    newParam.set_language(temp[1]);
                    languageOk = true;
                    break;
                case "sound" :
                    newParam.set_sound(Integer.valueOf(temp[1]));
                    soundOk = true;
                    break;
                case "light" :
                    newParam.set_light(Integer.valueOf(temp[1]));
                    lightOk = true;
                    break;
            }
        }

        if(languageOk && soundOk && lightOk){
            return newParam;
        }
        else {
            return null;
        }
    }
}