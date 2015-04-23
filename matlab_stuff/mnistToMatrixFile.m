%function [data] = mnistToMatrixFile(imgFile, labelFile, readDigits, offset)
    readDigits = 60000;
    d = readMNIST('D:\ITU\algorithms_seminar\mnist\train-images.idx3-ubyte', 'D:\ITU\algorithms_seminar\mnist\train-labels.idx1-ubyte', readDigits,0);
    %d = readMNIST(imgFile, labelFile, readDigits, offset);
    %data = cell(readDigits);
    data = cell(readDigits);
    
    
    for i = 1:readDigits
        data{i} = d(:,:,i);
        dlmwrite('myFile.txt',data{i}, '-append','delimiter',' ', 'roffset', 1);
    end
    
    
    
    %data = d(:,:,:);
% disp(data(:,:,3));
     %disp(data{1});

%end