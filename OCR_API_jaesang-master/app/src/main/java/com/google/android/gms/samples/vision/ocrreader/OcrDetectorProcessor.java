/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */

        // 여기까지 앱이 TextRecognizer의 detect 메서드를 사용해 개별 프레임에 있는 문자를 확인할 수 있었다.
        // 만약 다른 이미지파일이나 사진에서 텍스트를 찾기 원한다면 그렇게 하면 된다.
        // 그러나 카메라에서 바로 텍스트를 읽기 원한다면 Processor를 실행해야 한다.
        // Processor는 사용가능 할때마다 탐색을 처리할 것이다.
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {





    // 인터페이스가 두가지 메서드를 implement 시키도록 한다.
    // 첫째는 receiveDetections이다. 이는 사용가능하면 TextRecognizer로부터 TextBlcok을 받는 것이다.
    // 둘째는 release이다. TextRecognizer가 처리되면 자원을 없앨 것이다.
    // 이 경우 우리는 오직 깔끔한 그래픽 오버레이를 가질 것이다. OcrGraphic 객체를 통해서.

    private GraphicOverlay<OcrGraphic> graphicOverlay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        graphicOverlay = ocrGraphicOverlay;
    }

    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.
    // TextBlcok을 탐지해서 가져오고, 프로세서가 탐지하는 각 text block에 대해 OcrGraphic 객체를 만들자.

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        MySQLiteOpenHelper helper;
        String dbName = "st_file.db";
        int dbVersion = 1; // 데이터베이스 버전
        SQLiteDatabase db;
        String tag = "SQLite"; // Log 에 사용할 tag

        //helper = new MySQLiteOpenHelper(this ,dbName,null,dbVersion);


        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for(int i=0; i<items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if(item != null && item.getValue() != null) {
                Log.d("Processor", "Text detected! " + item.getValue());
            OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
            graphicOverlay.add(graphic);
        }
        }
    }

    @Override
    public void release() {
        graphicOverlay.clear();

    }
        // 이제 프로세서가 준비되었으므로 textRecognizer를 사용하도록 세팅하자.
        // TextRecognizer로 돌아가 createCameraSource 메서드를 다시 정리한다.

}
